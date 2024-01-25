package com.stock.main.config;

public class CustomerException extends Exception {

    public static class DataIsNull extends Exception {
        public DataIsNull() {
        }

        public DataIsNull(String message) {
            super(message);
        }
    }

    public static class DataNotFound extends Exception {
        public DataNotFound() {
        }

        public DataNotFound(String message) {
            super(message);
        }
    }

    public static class DataDuplicate extends Exception {
        public DataDuplicate() {
        }

        public DataDuplicate(String message) {
            super(message);
        }
    }
}
