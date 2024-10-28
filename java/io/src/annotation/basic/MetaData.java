package annotation.basic;

@AnnoMeta // 타입에 적용
public class MetaData {

    // @AnnoMeta // 필드 적용 X
    private String id;

    @AnnoMeta // 메소드에 적용
    public void call() {

    }

    public static void main(String[] args) throws NoSuchMethodException {
        AnnoMeta annoMeta = MetaData.class.getAnnotation(AnnoMeta.class);
        System.out.println("annoMeta = " + annoMeta);

        AnnoMeta methodAnno = MetaData.class.getMethod("call").getAnnotation(AnnoMeta.class);
        System.out.println("methodAnno = " + methodAnno);
    }

    /*
        annoMeta = @annotation.basic.AnnoMeta()
        methodAnno = @annotation.basic.AnnoMeta()
     */
}
