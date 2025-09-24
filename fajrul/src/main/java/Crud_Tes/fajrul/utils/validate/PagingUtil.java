package Crud_Tes.fajrul.utils.validate;

public class PagingUtil {
    public static Integer validatePage(Integer page){
        return page <= 0 ? 1 : page;
    }

    public static Integer validateSize(Integer size){
        return size <= 0 ? 10 : size;
    }

    public static String validateDirection(String direction){
        String upperDirection = direction.toUpperCase();
        return upperDirection.equals("ASC") || upperDirection.equals("DESC") ? upperDirection : "ASC";
    }
}