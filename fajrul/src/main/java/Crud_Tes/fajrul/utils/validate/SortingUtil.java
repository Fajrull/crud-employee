package Crud_Tes.fajrul.utils.validate;

public class SortingUtil {
    public static <T> String sortByValidation(Class<T> clazz, String sort, String defaultSortBy){
        try {
            return clazz.getDeclaredField(sort).getName();
        } catch (NoSuchFieldException e) {
            return defaultSortBy;
        }
    }
}