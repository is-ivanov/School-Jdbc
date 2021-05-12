package ua.com.foxminded.sqljdbcschool.ui.menuitem;

public class MenuItemQuit extends MenuItem {
    
    public MenuItemQuit(String name) {
        super(name);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        return true;
    }
    
}
