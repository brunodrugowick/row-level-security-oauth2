package dev.drugowick.theapiboilerplate.domain.auth;

import dev.drugowick.theapiboilerplate.config.EntitySecurityInfo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class AuthUtils {

    public static List<Long> getEntityIds(EntitySecurityInfo entityPrefixEnum) {
        var userAuthorities = getUserAuthorities();
        var entityPrefix = entityPrefixEnum.getSingleEntityAccessRolePrefix();
        var ids = new ArrayList<Long>();

        for (String userAuthority : userAuthorities) {
            if (userAuthority.startsWith(entityPrefix)) {
                var entityId = extractEntityId(entityPrefix, userAuthority);
                ids.add(entityId);
            }
        }

        return ids;
    }

    public static Set<String> getUserAuthorities() {
        Authentication auth = getAuthentication();
        return AuthorityUtils.authorityListToSet(auth.getAuthorities());
    }

    private static Long extractEntityId(String prefix, String authority) {
        var idPortion = authority.replace(prefix, "");
        try {
            return Long.parseLong(idPortion);
        } catch (NumberFormatException exception) {
            return null;
        }
    }

    private static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}

