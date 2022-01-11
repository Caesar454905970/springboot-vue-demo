package com.example.demo.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.example.demo.common.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;
@RestController
@RequestMapping("/files")
public class FileController  {

    //获取后端服务端口
    @Value("${server.port}")
    private String port;
    //获取后端服务IP
    @Value("${file.ip}")
    private String ip;


    /**
     * 文件上传接口
     * @param file
     * @return
     * @throws IOException
     */
    @CrossOrigin //解决跨域
    @PostMapping("/upload")
    public Result<?> upload(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename(); //获取源文件的文件名称
        //定义文件的唯一标识（前缀）
        String flag = IdUtil.fastSimpleUUID();
        //获取当前项目的目录  + files所在路径 + 文件名称
        String rootFilePath = System.getProperty("user.dir")+"/springboot/src/main/resources/files/"+flag+"_" + originalFilename;
        //前端获取的字节流写入
        FileUtil.writeBytes(file.getBytes(),rootFilePath); //把文件写入到上传的路径
        return Result.success("http://"+ip+":"+port+"/files/"+flag); //返回结果url
    }

    /**
     * 富文本文件上传接口
     * @param file
     * @return
     * @throws IOException
     */
    @CrossOrigin //解决跨域
    @PostMapping("/editor/upload")
    public JSONObject editorUpload(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename(); //获取源文件的文件名称
        //定义文件的唯一标识（前缀）
        String flag = IdUtil.fastSimpleUUID();
        //获取当前项目的目录  + files所在路径 + 文件名称
        String rootFilePath = System.getProperty("user.dir")+"/springboot/src/main/resources/files/"+flag+"_" + originalFilename; //获取上传的路径
        File saveFile = new File(rootFilePath);
        if(!saveFile.getParentFile().exists()){
            //如果文件夹目录不存在，则需要创建一个新的文件夹
            saveFile.getParentFile().mkdirs();
        }
        //前端获取的字节流写入
        FileUtil.writeBytes(file.getBytes(),rootFilePath); //把文件写入到上传的路径
        String url = saveFile.getPath(); Result.success("http://"+ip+":"+port+"/files/"+flag); //返回结果url

        //返回特定的json字符串格式
  /*
        {
            // errno 即错误代码，0 表示没有错误。
            //       如果有错误，errno != 0，可通过下文中的监听函数 fail 拿到该错误码进行自定义处理
            "errno": 0,
                // data 是一个数组，返回图片Object，Object中包含需要包含url、alt和href三个属性,它们分别代表图片地址、图片文字说明和跳转链接,alt和href属性是可选的，可以不设置或设置为空字符串,需要注意的是url是一定要填的。
                "data": [
            {
                url: "图片地址",
            },
    ]
        }*/
        //返回特定的json字符串格式
        //定义一个json的对象
        JSONObject json = new JSONObject();
        json.set("errno",0);
        //定义一个arr空数组对象：用于存放data数组
        JSONArray arr =new JSONArray();
        //定义一个data数组中的对象
        JSONObject data =new JSONObject();
        arr.add(data); //存放data数组
        data.set("url",url);//设置数组的对象元素
        json.set("data",arr);//把数组放入最外层的对象
        return json;

    }




    /**
     * 文件下载
     * @param flag
     * @param response
     */
    @GetMapping("/{flag}") //传入文件的唯一标识
    public void getFiles(@PathVariable String flag, HttpServletResponse response) {
        OutputStream os;  // 新建一个输出流对象

        String basePath = System.getProperty("user.dir") + "/springboot/src/main/resources/files/"; //服务器文件的存放位置
        List<String> fileNames = FileUtil.listFileNames(basePath);  // 获取所有的文件名称
        String fileName = fileNames.stream().filter(name -> name.contains(flag)).findAny().orElse("");  // 找到跟参数一致的文件
        try {
            if (StrUtil.isNotEmpty(fileName)) {
                response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
                response.setContentType("application/octet-stream");
                byte[] bytes = FileUtil.readBytes(basePath + fileName);  // 通过文件的路径读取文件字节流
                os = response.getOutputStream();   // 通过输出流返回文件
                os.write(bytes);
                os.flush();
                os.close();
            }
        } catch (Exception e) {
            System.out.println("文件下载失败");
        }
    }
}
