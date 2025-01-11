package co.simplon.sockwork_business.dtos;

public record AccountCreate(
        String username,
        String password
) {
    @Override
    public String toString() {
        return "AccountCreate{" +
                "username='" + username + '\'' +
                ", password='[PROTECTED]" +  '\'' +
                '}';
    }
}
