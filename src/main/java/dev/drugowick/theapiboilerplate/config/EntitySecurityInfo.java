package dev.drugowick.theapiboilerplate.config;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * This enum specifies the authorities/roles a user must have to access each entity of the whole app.
 *
 * For example, to access `card` entities a user should have `CARDS_{ID}` role for each card {id} they want to access,
 * as well as a `CARDS_WRITER` to create card entities.
 *
 * This approach works for the intent of this application, but it has several problems:
 *  - Assumes IDs are Long (will work with UUIDs with only a few modifications)
 *  - You can mess things up if you have resources with confusing names on your API.
 *  - Things get even more confusing if your resource doesn't have a plural distinct from the singular.
 *  - ...
 */
@AllArgsConstructor @Getter
public enum EntitySecurityInfo {
    EXAMPLE("EXAMPLE_", "EXAMPLES_USER");
    // Other API resources that you want to protect here

    private final String singleEntityAccessRolePrefix;
    private final String entityCreationRole;
}
