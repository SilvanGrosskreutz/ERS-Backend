package com.revature.models;

public enum Status {
	
	PENDING {
        @Override
        public String toString() {
            return "Pending";
        }
    },
    ACCEPTED {
        @Override
        public String toString() {
            return "Accepted";
        }
    },
    DENIED {
        @Override
        public String toString() {
            return "Denied";
        }
    }

}
