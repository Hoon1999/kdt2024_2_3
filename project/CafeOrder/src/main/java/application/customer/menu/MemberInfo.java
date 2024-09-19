package application.customer.menu;

public class MemberInfo {
    private static MemberInfo memberInfo = null;
    private int id; // 회원번호. 비회원은 0 이다.
    private int point; // 적립금.

    public MemberInfo(int id, int point) {
        if(memberInfo == null) {
            memberInfo = this;
        }

        this.id = id;
        this.point = point;
    }
    public static MemberInfo get() {
        return memberInfo;
    }

    public int getId() {
        return id;
    }

    public int getPoint() {
        return point;
    }


}
