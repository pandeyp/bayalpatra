package commons

class Parser {

    static Double parseToDouble(anything){
        Double result = 0
        if(anything){
            if(anything.getClass()==java.lang.Double){
                result=anything
            }else{
                try{
                    result = Double.parseDouble(anything)
                }catch (Exception e){}
            }

        }
        return result
    }

}
