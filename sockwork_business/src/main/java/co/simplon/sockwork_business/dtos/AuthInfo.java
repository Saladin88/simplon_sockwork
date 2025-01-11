package co.simplon.sockwork_business.dtos;

import java.util.List;
import java.util.Set;


public record AuthInfo(String token,
                       Set<String> roles) {
    @Override
    public String toString() {
        return "AuthInfo{" +
                "token=' [PROTECTED]" + '\'' +
                ", roles=" + roles +
                '}';
    }
}
