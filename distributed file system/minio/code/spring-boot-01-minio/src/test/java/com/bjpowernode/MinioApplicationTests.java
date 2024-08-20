package com.bjpowernode;

import com.bjpowernode.service.MinIOService;
import io.minio.*;
import io.minio.http.Method;
import io.minio.messages.Bucket;
import io.minio.messages.Item;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class MinioApplicationTests {

    @Resource
    private MinIOService minIOService;

    @Resource
    private MinioClient minioClient;

    @Test
    void contextLoads() {
        minIOService.testMinIOClient();
    }

    /**
     * 测试方法用于验证指定的存储桶是否存在于MinIO服务器上。
     * <p>
     * 此测试执行以下操作：
     * <ul>
     *     <li>使用 {@link MinioClient#bucketExists(BucketExistsArgs)} 方法检查 "myfile" 存储桶是否存在。</li>
     *     <li>打印存储桶是否存在到控制台。</li>
     * </ul>
     * <p>
     * 注意: 这个测试假定您已经正确配置了 MinioClient 并且可以连接到 MinIO 服务器。
     *
     * @throws Exception 如果与 MinIO 服务器的交互过程中发生错误
     */
    @Test
    void test01() throws Exception {
        boolean isBucketExists = minioClient.bucketExists(BucketExistsArgs.builder().bucket("myfile").build());
        System.out.println("myfile目录是否存在：" + isBucketExists);
    }

    /**
     * 测试方法用于验证指定的存储桶是否存在于MinIO服务器上，并在不存在时创建该存储桶。
     * <p>
     * 此测试执行以下操作：
     * <ul>
     *     <li>使用 {@link MinioClient#bucketExists(BucketExistsArgs)} 方法检查 "myfile2" 存储桶是否存在。</li>
     *     <li>如果存储桶不存在，则使用 {@link MinioClient#makeBucket(MakeBucketArgs)} 方法创建它。</li>
     *     <li>如果存储桶已经存在，则输出一条消息到控制台。</li>
     *     <li>设置存储桶的访问策略，允许公开读取权限。</li>
     * </ul>
     * <p>
     * 注意: 这个测试假定您已经正确配置了 MinioClient 并且可以连接到 MinIO 服务器。
     *
     * @throws Exception 如果与 MinIO 服务器的交互过程中发生错误
     */
    @Test
    void test02() throws Exception {
        String bucketName = "myfile2";
        boolean isBucketExists = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
        if (!isBucketExists) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
        } else {
            System.out.println("bucket已经存在，不需要创建.");
        }

        String policyJsonString = "{\"Version\" : \"2012-10-17\",\"Statement\":[{\"Sid\":\"PublicRead\",\"Effect\":\"Allow\",\"Principal\":{\"AWS\":\"*\"},\"Action\":[\"s3:GetObject\"],\"Resource\":[\"arn:aws:s3:::" + bucketName + "/*\"]}]}";
        //创建存储桶的时候，设置该存储桶里面的文件的访问策略，运行公开的读；
        minioClient.setBucketPolicy(SetBucketPolicyArgs.builder()
                .bucket(bucketName)
                .config(policyJsonString)//json串，里面是访问策略
                .build());
    }

    /**
     * 测试方法用于验证能够成功从MinIO服务器获取所有存储桶的列表，并打印出每个存储桶的名称及其创建日期。
     * <p>
     * 此测试执行以下操作：
     * <ul>
     *     <li>使用 {@link MinioClient#listBuckets()} 方法获取所有存储桶的列表。</li>
     *     <li>遍历存储桶列表，并使用 {@link Bucket#name()} 和 {@link Bucket#creationDate()} 方法获取每个存储桶的名称和创建日期。</li>
     *     <li>将每个存储桶的名称和创建日期打印到控制台。</li>
     * </ul>
     * <p>
     * 注意: 这个测试假定您已经正确配置了 MinioClient 并且可以连接到 MinIO 服务器。
     *
     * @throws Exception 如果与 MinIO 服务器的交互过程中发生错误
     */
    @Test
    void test03() throws Exception {
        List<Bucket> bucketList = minioClient.listBuckets();
        bucketList.forEach(bucket -> {
            System.out.println(bucket.name() + " -- " + bucket.creationDate());
        });
    }

    /**
     * 测试方法用于验证能否成功删除MinIO服务器上的指定存储桶。
     * <p>
     * 此测试执行以下操作：
     * <ul>
     *     <li>使用 {@link MinioClient#removeBucket(io.minio.RemoveBucketArgs)} 方法尝试删除名为 "myfile2" 的存储桶。</li>
     * </ul>
     * <p>
     * 注意:
     * <ul>
     *     <li>该测试假设 "myfile2" 存储桶已经存在于MinIO服务器上并且可以被删除。</li>
     *     <li>如果存储桶不存在或已被删除，则此方法将不会抛出异常。</li>
     *     <li>如果在删除过程中遇到任何问题（例如网络问题或权限问题），则会抛出异常。</li>
     * </ul>
     *
     * @throws Exception 如果与 MinIO 服务器的交互过程中发生错误
     */
    @Test
    void test04() throws Exception {
        minioClient.removeBucket(RemoveBucketArgs.builder().bucket("myfile2").build());
    }

    //---------------------------------------------------------------------------

    /**
     * 测试方法用于验证能否成功将文件上传到MinIO服务器上的指定存储桶。
     * <p>
     * 此测试执行以下操作：
     * <ul>
     *     <li>使用 {@link FileInputStream} 读取本地文件 "D:\\make\\1.png"。</li>
     *     <li>使用 {@link MinioClient#putObject(PutObjectArgs)} 方法将文件流上传到名为 "myfile" 的存储桶中，并命名为 "test.jpg"。</li>
     *     <li>打印上传响应信息。</li>
     *     <li>使用 {@link MinioClient#uploadObject(UploadObjectArgs)} 方法上传同一文件到同一存储桶，但文件名为 "test2.jpg"。</li>
     *     <li>再次打印上传响应信息。</li>
     * </ul>
     * <p>
     * 注意:
     * <ul>
     *     <li>该测试假设 "myfile" 存储桶已经存在于MinIO服务器上，并且具有写入权限。</li>
     *     <li>如果文件上传过程中遇到任何问题（例如网络问题或权限问题），则会抛出异常。</li>
     * </ul>
     *
     * @throws Exception 如果与 MinIO 服务器的交互过程中发生错误
     */
    @Test
    void test05() throws Exception {
        File file = new File("D:\\make\\1.png");
        ObjectWriteResponse objectWriteResponse = minioClient.putObject(PutObjectArgs.builder()
                .bucket("myfile")
                .object("test.jpg")
                .stream(new FileInputStream(file), file.length(), -1)
                .build()
        );
        System.out.println(objectWriteResponse);

        ObjectWriteResponse objectWriteResponse2 = minioClient.uploadObject(UploadObjectArgs.builder()
                .bucket("myfile")
                .object("test2.jpg")
                .filename("D:\\make\\1.png")
                .build()
        );
        System.out.println(objectWriteResponse);
    }

    /**
     * 测试方法用于验证能否成功获取MinIO服务器上指定对象的元数据信息。
     * <p>
     * 此测试执行以下操作：
     * <ul>
     *     <li>使用 {@link MinioClient#statObject(StatObjectArgs)} 方法获取存储桶 "myfile" 中名为 "test.jpg" 的对象的元数据信息。</li>
     *     <li>打印一条简单的消息 "hello world" 到控制台。</li>
     *     <li>打印获取到的对象元数据信息到控制台。</li>
     * </ul>
     * <p>
     * 注意:
     * <ul>
     *     <li>该测试假设 "myfile" 存储桶已经存在于MinIO服务器上，并且包含名为 "test.jpg" 的对象。</li>
     *     <li>如果对象获取过程中遇到任何问题（例如网络问题或权限问题），则会抛出异常。</li>
     * </ul>
     *
     * @throws Exception 如果与 MinIO 服务器的交互过程中发生错误
     */
    @Test
    void test06() throws Exception {
        StatObjectResponse statObjectResponse = minioClient.statObject(StatObjectArgs.builder()
                .bucket("myfile")
                .object("test.jpg")
                .build()
        );
        System.out.println("hello world");
        System.out.println(statObjectResponse);
    }

    /**
     * 测试方法用于验证能否成功获取MinIO服务器上指定对象的预签名URL。
     * <p>
     * 此测试执行以下操作：
     * <ul>
     *     <li>使用 {@link MinioClient#getPresignedObjectUrl(GetPresignedObjectUrlArgs)} 方法获取存储桶 "myfile" 中名为 "test.jpg" 的对象的预签名URL。</li>
     *     <li>设置预签名URL的有效期为7天。</li>
     *     <li>设置请求方法为 GET。</li>
     *     <li>打印生成的预签名URL到控制台。</li>
     * </ul>
     * <p>
     * 注意:
     * <ul>
     *     <li>该测试假设 "myfile" 存储桶已经存在于MinIO服务器上，并且包含名为 "test.jpg" 的对象。</li>
     *     <li>生成的预签名URL可以在有效期之内直接用于访问 "test.jpg" 对象，无需使用MinIO客户端或认证信息。</li>
     * </ul>
     *
     * @throws Exception 如果与 MinIO 服务器的交互过程中发生错误
     */
    @Test
    void test07() throws Exception {
        String presignedObjectUrl = minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                .bucket("myfile")
                .object("test.jpg")
                .expiry(7, TimeUnit.DAYS)
                .method(Method.GET)
                .build());
        System.out.println(presignedObjectUrl);
    }

    /**
     * 测试方法用于验证能否成功从MinIO服务器下载指定的对象并保存到本地文件系统。
     * <p>
     * 此测试执行以下操作：
     * <ul>
     *     <li>使用 {@link MinioClient#getObject(GetObjectArgs)} 方法从存储桶 "myfile" 中下载名为 "test.jpg" 的对象。</li>
     *     <li>将下载的数据流传输到本地文件 "D:\\MinIO\\123.jpg"。</li>
     *     <li>打印传输过程中的字节数到控制台。</li>
     * </ul>
     * <p>
     * 注意:
     * <ul>
     *     <li>该测试假设 "myfile" 存储桶已经存在于MinIO服务器上，并且包含名为 "test.jpg" 的对象。</li>
     *     <li>此测试会覆盖目标路径上的现有文件。</li>
     *     <li>确保目标路径 "D:\\MinIO\\" 是可写的，并且父目录已经存在。</li>
     * </ul>
     *
     * @throws IOException 如果在文件I/O操作过程中发生错误
     */
    @Test
    void test08() throws Exception {
        GetObjectResponse getObjectResponse = minioClient.getObject(GetObjectArgs.builder()
                .bucket("myfile")
                .object("test.jpg")
                .build());

        System.out.println(getObjectResponse.transferTo(new FileOutputStream("D:\\MinIO\\123.jpg")));
    }

    /**
     * 测试方法用于验证能否成功从MinIO服务器的指定存储桶中获取所有对象的列表。
     * <p>
     * 此测试执行以下操作：
     * <ul>
     *     <li>使用 {@link MinioClient#listObjects(ListObjectsArgs)} 方法获取存储桶 "myfile" 中的所有对象。</li>
     *     <li>遍历每个对象并打印其名称到控制台。</li>
     * </ul>
     * <p>
     * 注意:
     * <ul>
     *     <li>该测试假设 "myfile" 存储桶已经存在于MinIO服务器上，并且包含至少一个对象。</li>
     *     <li>如果存储桶为空，则不会有任何输出。</li>
     * </ul>
     *
     * @throws Exception 如果在与MinIO服务器交互的过程中发生错误
     */
    @Test
    void test09() throws Exception {
        Iterable<Result<Item>> listObjects = minioClient.listObjects(ListObjectsArgs.builder()
                .bucket("myfile")
                .build());

        listObjects.forEach( itemResult -> {
            try {
                Item item = itemResult.get();
                System.out.println(item.objectName());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * 测试方法用于验证能否成功从MinIO服务器的指定存储桶中删除对象。
     * <p>
     * 此测试执行以下操作：
     * <ul>
     *     <li>使用 {@link MinioClient#removeObject(RemoveObjectArgs)} 方法删除存储桶 "myfile" 中名为 "test.jpg" 的对象。</li>
     * </ul>
     * <p>
     * 注意:
     * <ul>
     *     <li>该测试假设 "myfile" 存储桶已经存在于MinIO服务器上，并且其中存在名为 "test.jpg" 的对象。</li>
     *     <li>如果存储桶不存在或对象不存在，则此方法将不会抛出异常，但也不会有实际的效果。</li>
     * </ul>
     *
     * @throws Exception 如果在与MinIO服务器交互的过程中发生错误
     */
    @Test
    void test10() throws Exception {
        minioClient.removeObject(RemoveObjectArgs.builder()
                .bucket("myfile")
                .object("test.jpg")
                .build());
    }
}
