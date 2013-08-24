package utilits.controller.users;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;

import java.util.*;

/**
 * Here will be javadoc
 *
 * @author karlovsky
 * @since 2.4, 3/28/13
 */
public enum Authority implements GrantedAuthority {

    ROOT(               0x1,   "ROLE_ROOT"),
    EQUIPMENT_EDIT(     0x2,   "ROLE_EQUIPMENT_EDIT"),
    EQUIPMENT_VIEW(     0x4,   "ROLE_EQUIPMENT_VIEW"),
    ACCOUNT_EDIT(       0x8,   "ROLE_ACCOUNT_EDIT"),
    ACCOUNT_VIEW(       0x10,  "ROLE_ACCOUNT_VIEW"),
    COMMUNIGATE_VIEW(   0x20,  "ROLE_COMMUNIGATE_VIEW"),
    COMMUNIGATE_EDIT(   0x40,  "ROLE_COMMUNIGATE_EDIT"),
    CAPACITY_VIEW(      0x80,  "ROLE_CAPACITY_VIEW"),
    CAPACITY_MANAGER(   0x200, "ROLE_CAPACITY_MANAGER"),
    CAPACITY_EDIT(      0x100, "ROLE_CAPACITY_EDIT");

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
        Collection<Authority> validatedAuthorities = makeValidatedAuthorities(authorities);
        for (Authority authority : validatedAuthorities) {
            authorityMask |= authority.getCode();
        }
        return authorityMask;
    }

    private static Collection<Authority> makeValidatedAuthorities(String authorities) {
        Collection<Authority> result = new HashSet<Authority>();
        if (StringUtils.isNotEmpty(authorities)) {
            for (Authority authority : values()) {
                for (String s : authorities.split(",")) {
                    Authority added = Authority.valueOf(s.trim());
                    if (authority == added) {
                        switch (added) {
                            case ROOT:
                                Collections.addAll(result, values());
                                return result;
                            case EQUIPMENT_EDIT:
                                result.add(EQUIPMENT_EDIT);
                                result.add(EQUIPMENT_VIEW);
                                break;
                            case ACCOUNT_EDIT:
                                result.add(ACCOUNT_EDIT);
                                result.add(ACCOUNT_VIEW);
                                break;
                            case COMMUNIGATE_EDIT:
                                result.add(COMMUNIGATE_EDIT);
                                result.add(COMMUNIGATE_VIEW);
                                break;
                            case CAPACITY_MANAGER:
                                result.add(CAPACITY_MANAGER);
                                result.add(CAPACITY_VIEW);
                                break;
                            case CAPACITY_EDIT:
                                result.add(CAPACITY_EDIT);
                                result.add(CAPACITY_MANAGER);
                                result.add(CAPACITY_VIEW);
                                break;
                            default:
                                result.add(added);
                        }
                    }
                }
            }
        }
        return result;
    }

}
