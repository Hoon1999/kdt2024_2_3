package cafeorder;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class CDBC {
    private static CDBC cdbc = null;
    private static Connection con = null;
    private static String server; // MySQL 서버 주소
    private static String database; // MySQL DATABASE 이름
    private static String user_name; //  MySQL 서버 아이디
    private static String password; // MySQL 서버 비밀번호
    private static PreparedStatement pstmt;
    private static PreparedStatement pstmt2; // ao 에 사용할 pstmt
    private static Statement stmt;
    private static ResultSet rs;
    CDBC() {
        // config.properties 파일에서 변수 값 가져오기
        String propFile = "config/config.properties";
        Properties prop = new Properties();
        try {
            FileInputStream fis = new FileInputStream(propFile);
            prop.load(new BufferedInputStream(fis));
            server = prop.getProperty("server");
            database = prop.getProperty("database");
            user_name = prop.getProperty("user_name");
            password = prop.getProperty("password");
        } catch(IOException e) {
            e.printStackTrace();
        }

        // 1.드라이버 로딩
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println(" !! <JDBC 오류> Driver load 오류: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public static void connect() {
        // 2.연결
        try {
            con = DriverManager.getConnection("jdbc:mysql://" + server + "/" + database + "?useSSL=false&allowPublicKeyRetrieval=true", user_name, password);
            System.out.println("정상적으로 연결되었습니다.");
        } catch(SQLException e) {
            System.err.println("con 오류:" + e.getMessage());
            e.printStackTrace();
        }
    }
    public static void disconnect() {
        // 3.해제
        try {
            if(con != null)
                con.close();
        } catch (SQLException e) {}
    }


    /**
     * 주문을 넣습니다.
     * @param orderList
     * 주문내역을 json 으로 받습니다.
     * 주문내역의 형식은 다음과 같습니다.
     * {id: 회원번호, order_type: 0(매장) or 1(포장)
     *  products:[{id: 상품번호, count: 상품개수, options:[id: 옵션번호, count: 옵션개수]}, ... ]
     * }
     */
    public static JSONObject order(JSONObject orderList) {
        if(cdbc == null) {
            cdbc = new CDBC();
        }

        connect();

        int order_id = 0, price = -1, order_detail_id;
        String sql = "insert into `order` (member_id, order_type, payment_time) values(?, ?, NOW())";
        String odSql = "insert into order_detail(order_id, product_id, `count`) values(?, ?, ?)";
        String aoSql = "insert into additional_option(order_detail_id, option_id, `count`)  values(?, ?, ?)";
//        String testInput = "{\"id\": 3, \"products\":[{\"id\": 4, \"count\": 2, \"options\":[{\"id\": 5, \"count\": 1}]}]}";
//        String testInput = "{\"id\":1,\"order_type\":0,\"point\":0,\"products\":[{\"count\":1,\"options\":[],\"id\":3}]}";
//        orderList = new JSONObject(testInput);
        try {
            con.setAutoCommit(false); // start transaction
            pstmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS); // auto_increment 된 id 를 가져오기위한 인자
            pstmt.setInt(1, orderList.getInt("id")); // ? 자리에 값 삽입
            pstmt.setInt(2, orderList.getInt("order_type")); // ? 자리에 값 삽입
            int rt = pstmt.executeUpdate(); // 쿼리 실행
            if (rt == 0) throw new SQLException("insert into 를 실행했지만 행이 추가되지 않음");
            order_id = getAutoIncId(pstmt);

            JSONArray products = orderList.getJSONArray("products");
            for(int i = 0; i < products.length(); i++) {
                JSONObject p = (JSONObject) products.get(i);
                pstmt = con.prepareStatement(odSql, PreparedStatement.RETURN_GENERATED_KEYS);
                pstmt.setInt(1, order_id);
                pstmt.setInt(2, p.getInt("id")); // (예를 들면) 아메리카노
                pstmt.setInt(3, p.getInt("count")); // 3개
                rt = pstmt.executeUpdate();
                if (rt == 0) throw new SQLException("insert into 를 실행했지만 행이 추가되지 않음");
                order_detail_id = getAutoIncId(pstmt);

                JSONArray options = p.getJSONArray("options");
                for(int j = 0; j < options.length(); j++) {
                    JSONObject opt = (JSONObject) options.get(j);
                    pstmt2 = con.prepareStatement(aoSql);
                    pstmt2.setInt(1, order_detail_id);
                    pstmt2.setInt(2, opt.getInt("id")); // (예를 들면) 샷추가
                    pstmt2.setInt(3, opt.getInt("count")); // 3 개
                    pstmt2.executeUpdate();
                }

                stmt = con.createStatement();
                String qry = "select order_product_price(" + order_detail_id + ")";
                rs = stmt.executeQuery(qry);
                if(rs.next())
                    price = rs.getInt(1);
                System.out.println("price: " + price);

                pstmt2 = con.prepareStatement("update order_detail set price = ? * `count` where id = ?");
                pstmt2.setInt(1, price);
                pstmt2.setInt(2, order_detail_id);
                rt = pstmt2.executeUpdate();
                if (rt == 0) throw new SQLException("update set 을 실행했지만 행이 변경되지 않음");
            }


            String qry = "select ifnull(sum(price), 0) from order_detail where order_id = " + order_id ;
            rs = stmt.executeQuery(qry);
            if(rs.next())
                price = rs.getInt(1);
            qry = "update `order` set total_payment_amount = " + price + " where id = " + order_id;
            stmt.executeUpdate(qry);
            qry = "update `member` set `point` = " + orderList.getInt("point") + " where id = " + orderList.getInt("id");
            stmt.executeUpdate(qry);

            con.commit(); // end transaction


        } catch(SQLException e) {
            System.out.println("에러: "+ e.getMessage());
            System.out.println("트랜잭션 실패, 롤백 실행");
            if(con != null)
                try {
                    con.rollback(); // 롤 백
                } catch (SQLException sqle) {
                    sqle.printStackTrace();
                }
        }
        finally {
            if(rs != null)
                try {
                    rs.close();
                } catch(SQLException ex) {}
            if(stmt != null)
                try {
                    stmt.close();
                } catch(SQLException ex) {}
            if(pstmt2 != null)
                try {
                    pstmt2.close();
                } catch(SQLException ex) {}
            if(pstmt != null)
                try {
                    pstmt.close();
                } catch(SQLException ex) {}
        }
        disconnect();
        JSONObject obj =  new JSONObject();
        obj.put("data", order_id);
        return obj;
    }

    /**
     * 특정 상품에 연결된 옵션을 출력합니다.
     * @param product_id
     * @return
     */
    public static void getOptions(int product_id) {
        if(cdbc == null) {
            cdbc = new CDBC();
        }

        connect();

        String sql = "select a.옵션이름, a.옵션가격, a.옵션유형 from product p left join (select po.product_id as id, o.name 옵션이름 , o.price 옵션가격, ot.name 옵션유형 from product_option po join `option` o on po.option_id = o.id join option_type ot on o.option_type_id = ot.id ) as a on p.id = a.id join category c on p.category_id = c.id where p.id = ?";
        try{
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, product_id);
            rs = pstmt.executeQuery();
            while(rs.next()){
                System.out.println(rs.getString(1) + " " +
                        rs.getInt(2) + " " +
                        rs.getString(3));


            }


        } catch(SQLException e) {
            System.out.println("에러: " + e.getMessage());
        } finally {
            if(rs != null)
                try {
                    rs.close();
                } catch(SQLException sqle) {}
            if(pstmt != null)
                try {
                    pstmt.close();
                } catch(SQLException sqle) {}

        }

        disconnect();
    }

    /**
     * 회원을 조회하는 메서드입니다. 전화번호와 일치하는 회원의 번호와 적립금을 리턴합니다. 만약 존재하지 않는 회원이면 {id:0, point: 0} 을 리턴합니다.
     * @param phoneNumber
     * @return {id: 0, point: 0}
     */
    public static JSONObject findMemberId(String phoneNumber) {
        if(cdbc == null) {
            cdbc = new CDBC();
        }
        connect();
        int id = 0, point = 0;
        try {
            String qry = "select id, `point` from member where phone_number = \"" + phoneNumber +"\"" ;
            stmt = con.createStatement();
            rs = stmt.executeQuery(qry);
            if(rs.next()) {
                id = rs.getInt(1);
                point = rs.getInt(2);
            }

        } catch(SQLException e) {
            System.out.println("에러: " + e.getMessage());
        }
        finally {
            if(rs != null)
                try {
                    rs.close();
                } catch(SQLException sqle) {}
            if(stmt != null)
                try {
                    stmt.close();
                } catch(SQLException sqle) {}
        }

        JSONObject result = new JSONObject();
        result.put("id", id);
        result.put("point", point);
        disconnect();
        return result;
    }

    /**
     * 회원을 추가하는 메서드입니다.
     * @param phoneNumber
     */
    public static void joinMember(String phoneNumber) {
        String qry = "insert into member(phone_number) values (\"" + phoneNumber + "\")";
        simpleIUDQeury(qry);
    }

    /**
     * 카테고리를 추가하는 메서드입니다.
     * @param name 카테고리 이름
     */
    public static void addCategory(String name) {
        String qry ="insert into category(name) values (\"" + name + "\")";
        simpleIUDQeury(qry);
    }
    /**
     * 매개변수로 받은 qry 문을 바로 요청하는 함수입니다.
     * Insert, Update, Delete 문만 가능합니다.
     * @param qry
     */
    public static void simpleIUDQeury(String qry) {
        if(cdbc == null) {
            cdbc = new CDBC();
        }
        connect();
        try {
            stmt = con.createStatement();
            stmt.executeUpdate(qry);
        } catch(SQLException e) {
            System.out.println("에러: " + e.getMessage());
        }
        finally {
            if(stmt != null)
                try {
                    stmt.close();
                } catch(SQLException sqle) {}
        }
        disconnect();
    }

    /**
     * 모든 상품 목록을 가져오는 메서드입니다.
     */
    public static JSONObject getAllProducts() {
        if(cdbc == null) {
            cdbc = new CDBC();
        }
        connect();
        JSONArray productList = new JSONArray();
        try {
            String qry = "select * from product" ;
            stmt = con.createStatement();
            rs = stmt.executeQuery(qry);
            while(rs.next()){
                JSONObject product = new JSONObject();
                product.put("id", rs.getInt(1));
                product.put("category_id", rs.getInt(2));
                product.put("name", rs.getString(3));
                product.put("price", rs.getInt(4));
                product.put("stock_count", rs.getInt(5));
                product.put("out_of_stock", rs.getInt(6));
                product.put("image_path", rs.getString(7));

                String sql = "select * from product_option po join `option` o on po.option_id = o.id where product_id = ?";
                pstmt = con.prepareStatement(sql);
                pstmt.setInt(1, rs.getInt(1));
                ResultSet rs2 = pstmt.executeQuery();
                JSONArray options = new JSONArray();
                while(rs2.next()) {
                    JSONObject option = new JSONObject();
                    option.put("id", rs2.getInt(3));
                    option.put("name", rs2.getString(4));
                    option.put("price", rs2.getString(5));
                    option.put("stock_count", rs2.getString(6));
                    option.put("out_of_stock", rs2.getString(7));
                    option.put("option_type_id", rs2.getString(8));
                    options.put(option);
                }
                rs2.close();

                product.put("options", options);
                productList.put(product);
            }
        } catch(SQLException e) {
            System.out.println("에러: " + e.getMessage());
        }
        finally {
            if(rs != null)
                try {
                    rs.close();
                } catch(SQLException sqle) {}
            if(stmt != null)
                try {
                    stmt.close();
                } catch(SQLException sqle) {}
        }
        disconnect();
        JSONObject data = new JSONObject().put("data", productList);
        return data;
    }
    public static JSONObject getPopularProducts() {
        if(cdbc == null) {
            cdbc = new CDBC();
        }
        connect();
        JSONArray productList = new JSONArray();
        try {
            String qry = "select * from popular_product" ;
            stmt = con.createStatement();
            rs = stmt.executeQuery(qry);
            while(rs.next()){
                JSONObject product = new JSONObject();
                product.put("id", rs.getInt(1));
                product.put("sales_volume", rs.getInt(2));

                productList.put(product);
            }
        } catch(SQLException e) {
            System.out.println("에러: " + e.getMessage());
        }
        finally {
            if(rs != null)
                try {
                    rs.close();
                } catch(SQLException sqle) {}
            if(stmt != null)
                try {
                    stmt.close();
                } catch(SQLException sqle) {}
        }
        disconnect();
        JSONObject data = new JSONObject().put("data", productList);
        return data;
    }
    public static JSONObject getOptionTypes() {
        if(cdbc == null) {
            cdbc = new CDBC();
        }
        connect();
        JSONArray optionList = new JSONArray();
        try {
            String qry = "select * from option_type" ;
            stmt = con.createStatement();
            rs = stmt.executeQuery(qry);
            while(rs.next()){
                JSONObject option = new JSONObject();
                option.put("id", rs.getInt(1));
                option.put("name", rs.getString(2));
                option.put("priority", rs.getInt(3));
                option.put("duplicate", rs.getInt(4));

                optionList.put(option);
            }
        } catch(SQLException e) {
            System.out.println("에러: " + e.getMessage());
        }
        finally {
            if(rs != null)
                try {
                    rs.close();
                } catch(SQLException sqle) {}
            if(stmt != null)
                try {
                    stmt.close();
                } catch(SQLException sqle) {}
        }
        disconnect();
        JSONObject data = new JSONObject().put("data", optionList);
        return data;
    }
    public static JSONObject getCategories() {
        if(cdbc == null) {
            cdbc = new CDBC();
        }
        connect();
        JSONArray categoryList = new JSONArray();
        try {
            String qry = "select * from category" ;
            stmt = con.createStatement();
            rs = stmt.executeQuery(qry);
            while(rs.next()){
                JSONObject category = new JSONObject();
                category.put("id", rs.getInt(1));
                category.put("name", rs.getString(2));

                categoryList.put(category);
            }
        } catch(SQLException e) {
            System.out.println("에러: " + e.getMessage());
        }
        finally {
            if(rs != null)
                try {
                    rs.close();
                } catch(SQLException sqle) {}
            if(stmt != null)
                try {
                    stmt.close();
                } catch(SQLException sqle) {}
        }
        disconnect();
        JSONObject data = new JSONObject().put("data", categoryList);
        return data;
    }
    public static JSONObject getSales() {
        if(cdbc == null) {
            cdbc = new CDBC();
        }
        connect();
        JSONArray categoryList = new JSONArray();
        try {
            String qry = "select o.id, m.phone_number, o.total_payment_amount, o.payment_time from `order` o join member m on o.member_id = m.id" ;
            stmt = con.createStatement();
            rs = stmt.executeQuery(qry);
            while(rs.next()){
                JSONObject category = new JSONObject();
                category.put("id", rs.getInt(1));
                category.put("phoneNumber", rs.getString(2));
                category.put("paymentAmount", rs.getInt(3));
                category.put("paymentTime", rs.getString(4));

                categoryList.put(category);
            }
        } catch(SQLException e) {
            System.out.println("에러: " + e.getMessage());
        }
        finally {
            if(rs != null)
                try {
                    rs.close();
                } catch(SQLException sqle) {}
            if(stmt != null)
                try {
                    stmt.close();
                } catch(SQLException sqle) {}
        }
        disconnect();
        JSONObject data = new JSONObject().put("data", categoryList);
        return data;
    }
    public static JSONObject getDailySales() {
        if(cdbc == null) {
            cdbc = new CDBC();
        }
        connect();
        JSONArray categoryList = new JSONArray();
        try {
            String qry = "select Date(payment_time), sum(total_payment_amount) from `order` group by Date(payment_time)" ;
            stmt = con.createStatement();
            rs = stmt.executeQuery(qry);
            while(rs.next()){
                JSONObject category = new JSONObject();
                category.put("date", rs.getString(1));
                category.put("sales", rs.getInt(2));

                categoryList.put(category);
            }
        } catch(SQLException e) {
            System.out.println("에러: " + e.getMessage());
        }
        finally {
            if(rs != null)
                try {
                    rs.close();
                } catch(SQLException sqle) {}
            if(stmt != null)
                try {
                    stmt.close();
                } catch(SQLException sqle) {}
        }
        disconnect();
        JSONObject data = new JSONObject().put("data", categoryList);
        return data;
    }
    public static JSONObject getMonthlySales() {
        if(cdbc == null) {
            cdbc = new CDBC();
        }
        connect();
        JSONArray categoryList = new JSONArray();
        try {
            String qry = "SELECT DATE_FORMAT(payment_time, '%Y-%m'), sum(total_payment_amount) FROM `order` GROUP BY DATE_FORMAT(payment_time, '%Y-%m')" ;
            stmt = con.createStatement();
            rs = stmt.executeQuery(qry);
            while(rs.next()){
                JSONObject category = new JSONObject();
                category.put("date", rs.getString(1));
                category.put("Sales", rs.getInt(2));

                categoryList.put(category);
            }
        } catch(SQLException e) {
            System.out.println("에러: " + e.getMessage());
        }
        finally {
            if(rs != null)
                try {
                    rs.close();
                } catch(SQLException sqle) {}
            if(stmt != null)
                try {
                    stmt.close();
                } catch(SQLException sqle) {}
        }
        disconnect();
        JSONObject data = new JSONObject().put("data", categoryList);
        return data;
    }

    public static JSONObject getProductSales() {
        if(cdbc == null) {
            cdbc = new CDBC();
        }
        connect();
        JSONArray categoryList = new JSONArray();
        try {
            String qry = "select p.id, p.name, count(product_id) as `count` from order_detail od join product p on p.id = od.product_id group by product_id order by `count` desc limit 10" ;
            stmt = con.createStatement();
            rs = stmt.executeQuery(qry);
            while(rs.next()){
                JSONObject category = new JSONObject();
                category.put("id", rs.getInt(1));
                category.put("name", rs.getString(2));
                category.put("count", rs.getInt(3));

                categoryList.put(category);
            }
        } catch(SQLException e) {
            System.out.println("에러: " + e.getMessage());
        }
        finally {
            if(rs != null)
                try {
                    rs.close();
                } catch(SQLException sqle) {}
            if(stmt != null)
                try {
                    stmt.close();
                } catch(SQLException sqle) {}
        }
        disconnect();
        JSONObject data = new JSONObject().put("data", categoryList);
        return data;
    }

    public static int getAutoIncId(PreparedStatement pstmt) throws SQLException{
        if(pstmt == null)
            throw new SQLException("pstmt 를 실행한 뒤에 호출해야합니다.");

        // auto increment 된 id 가져오는 과정
        try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            }
            else {
                throw new SQLException("자동생성된 ID 를 가져올 수 없음");
            }
        }
    }
}
