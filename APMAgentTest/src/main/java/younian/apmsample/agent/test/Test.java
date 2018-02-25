package younian.apmsample.agent.test;

public class Test {

    public static void main(String[] args) {
        Test.testStatic();
        Test test = new Test();
        test.test1();
        test.test2();
        test.test3();
    }

    public void test1(){
        System.out.println("test1");
    }

    public void test2(){
        System.out.println("test2");
    }

    public void test3(){
        System.out.println("test3");
    }

    public static void testStatic(){
        System.out.println("testStatic");
    }
}
