package ua.com.foxminded.sqljdbcschool.domain;


import ua.com.foxminded.sqljdbcschool.exception.DAOException;

public class Application {

    public static void main(String[] args) throws DAOException {
        
        Facade facade = new Facade();
        facade.prepareBase();
            
        }

}
