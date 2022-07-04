package com.revature.models;

public enum Role {
	
	EMPLOYEE {
        @Override
        public String toString() {
            return "Employee";
        }
    },
    MANAGER {
        @Override
        public String toString() {
            return "Manager";
        }
    }
}
