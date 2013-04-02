package utilits.controller.users;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * Here will be javadoc
 *
 * @author karlovsky
 * @since 2.4, 3/28/13
 */
public enum Authority implements GrantedAuthority {

    ROOT(0x1, "ROLE_ROOT"),
    EQUIPMENT_EDIT(0x2, "ROLE_EQUIPMENT_EDIT"),
    EQUIPMENT_VIEW(0x4, "ROLE_EQUIPMENT_VIEW"),
    ACCOUNT_EDIT(0x8, "ROLE_ACCOUNT_EDIT"),
    ACCOUNT_VIEW(0x10, "ROLE_ACCOUNT_VIEW");

    private int code;
    private String name;

    private Authority(int code, String name) {
        this.code = code;
        this.name = name;
    }

    @Override
    public String getAuthority() {
        return name;
    }

    public int getCode() {
        return code;
    }

    public static Collection<? extends GrantedAuthority> getAuthorities(int mask) {
        Collection<Authority> authorities = new ArrayList<Authority>() {
            @Override
            public String toString() {
                Iterator<Authority> it = iterator();
                if (!it.hasNext()) {
                    return "";
                }
                StringBuilder sb = new StringBuilder();
                for (; ; ) {
                    Authority e = it.next();
                    sb.append(e);
                    if (!it.hasNext()) {
                        return sb.toString();
                    }
                    sb.append(',').append(' ');
                }
            }
        };
        for (Authority authority : values()) {
            if ((mask & authority.getCode()) != 0) {
                authorities.add(authority);
            }
        }
        return authorities;
    }

    public static int getAuthorityMask(String authorities) {
        int authorityMask = 0;
        if (StringUtils.isNotEmpty(authorities)) {
            for (String s : authorities.split(",")) {
                Authority authority = Authority.valueOf(s.trim());
                authorityMask |= authority.getCode();
            }
        }
        return authorityMask;
    }

}
