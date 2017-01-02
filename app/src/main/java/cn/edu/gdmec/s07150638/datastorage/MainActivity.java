package cn.edu.gdmec.s07150638.datastorage;

import android.content.SharedPreferences;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    private TextView t1;
    private EditText et1,et2;
    private Button bt1,bt2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        t1 = (TextView) findViewById(R.id.t1);
        et1 = (EditText) findViewById(R.id.et1);
        et2 = (EditText) findViewById(R.id.et2);
        bt1 = (Button) findViewById(R.id.bt1);
        bt2 = (Button) findViewById(R.id.bt2);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WriteSharedPreferences();
            }
        });
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReadSharedPreferences();
            }
        });
    }

    private void ReadSharedPreferences(){
        String strName,strPassword;
        SharedPreferences user = getSharedPreferences("user", 0);
        strName = user.getString("NAME","");
        strPassword = user.getString("PASSWORD","");
        t1.setText("strName = " + strName + ",strPassword = " + strPassword);
    }

    private void WriteSharedPreferences(){
        SharedPreferences user = getSharedPreferences("user", 0);
        SharedPreferences.Editor editor = user.edit();
        editor.putString("NAME",et1.getText().toString());
        editor.putString("PASSWORD",et2.getText().toString());
        editor.commit();
        Toast.makeText(MainActivity.this, "提交成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        SubMenu file = menu.addSubMenu("文件");
        file.add(0,1,0,"打开");
        file.add(0,2,0,"编辑");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case 1:
                readFile("file");
                break;
            case 2:
                writeFile("file");
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void readFile(String sFileName){
        try {
            FileInputStream fis = openFileInput(sFileName);
            // 将字节流转换成字符流
            InputStreamReader inReader = new InputStreamReader(fis);
            // 转换成带缓存的BufferedReader
            BufferedReader bufferedReader = new BufferedReader(inReader);
            String s,sum="";
            while((s = bufferedReader.readLine()) != null){
                //Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
                sum += s;
            }
            t1.setText(sum);
            fis.close();
            Toast.makeText(MainActivity.this, "读取成功", Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeFile(String sFileName){
        String s = "Hello,";
        s += "\n" + et1.getText() + et2.getText();
        try {
            // 定义一个文件字节输出流，名字为sFileName
            FileOutputStream fos = openFileOutput(sFileName, 0);
            // 将文件字节输出流转换成文件字符输出流
            OutputStreamWriter outWrite = new OutputStreamWriter(fos);
            // 再将文件字符输出流转换成缓存字符输出流
            BufferedWriter bufferedWriter = new BufferedWriter(outWrite);
            // 使用write方法将信息写入文件
            bufferedWriter.write(s);
            bufferedWriter.flush();
            fos.close();
            Toast.makeText(MainActivity.this, "写入成功", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void readSD(String sName){
        // 获取SD卡根目录
        String sPath = android.os.Environment.getExternalStorageDirectory().getPath();
        String Path = sPath + "/" +sName;
        // 建立一个文件对象
        File file = new File(Path);
        // 接下来操作类似java的I/O文件操作
        int length = (int) file.length();
        // 使用字节数组存储取出来的数据
        byte[] b = new byte[length];
        try {
            FileInputStream fis = new FileInputStream(file);
            fis.read(b, 0, length);
            String data = "";
            for(byte element:b){
                data += element;
            }
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeSD(String sName,byte[]data){
        String PATH = android.os.Environment.getExternalStorageDirectory().getPath() + "/" + sName;
        File file = new File(PATH);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(data);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
