package entities;

public enum UserStatus {
    ACTIVE, // all features available
    BLOCKED, // can not login
    INACTIVE // all data has been removed and ready for hard delete and can not login
}
