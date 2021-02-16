package lang;
import lang.String;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description
 * @Date 2021-02-16
 * @Created by lixianchun
 */
@Slf4j
public class Test {

    /**
     * 系统自带的String会优先加载，除非是替换系统的String.class
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("");

        try{

        }catch (Exception e){
            log.error(e.getMessage());
        }
    }
}
