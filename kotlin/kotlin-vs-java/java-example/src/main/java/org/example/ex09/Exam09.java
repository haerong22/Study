package org.example.ex09;

/**
 * when
 */
public class Exam09 {
    
    public Exam09(StoreUser storeUser) {
        
        if (storeUser.getRole().equals("MASTER")) {
            
        } else if (storeUser.getRole().equals("ADMIN")) {
            
        } else if (storeUser.getRole().equals("USER")) {
            
        } else {
            
        }
        
        switch (storeUser.getRole()) {
            case "MASTER":
                break;
            case "ADMIN":
                break;
            case "USER":
                break;
            default:
        }
        
        try {
            
        } catch (Exception e) {
            if (e instanceof NullPointerException) {
                
            } else if (e instanceof NumberFormatException) {
                
            }
        }

    }

    public static void main(String[] args) {
        new Exam09(new StoreUser());
    }
}

class StoreUser {
    private String name;
    private String role;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}