package org.camunda.bpm.model.fluent;

/**
 * Mechanism used to locate activities within a process definition
 *
 * Pattern lifted shamelessly from org.openqa.selenium.By
 *
 */
public class By {

    /**
     * @param id The value of the "id" attribute to search for
     *
     * @return a By which locates activities by the value of the "id" attribute.
     */
    public static By id(final String id) {
        if (id == null)
            throw new IllegalArgumentException(
                    "Cannot find activity with a null id attribute.");

        return new ById(id);
    }

    /**
     * @param name The value of the "name" attribute to search for
     *
     * @return a By which locates activities by the value of the "name" attribute.
     */
    public static By name(final String name) {
        if (name == null)
            throw new IllegalArgumentException(
                    "Cannot find elements when name text is null.");

        return new ByName(name);
    }

    public static class ById extends By {
        private final String id;

        public ById(String id) {
            this.id = id;
        }

        @Override
        public String toString() {
            return "By.id: " + id;
        }
    }

    public static class ByName extends By {
        private final String name;

        public ByName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "By.name: " + name;
        }
    }
}