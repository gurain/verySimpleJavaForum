package com.my.forum;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.Scanner;

public class main  {
    //主机地址
    public static final String  HOST = "localhost";
    //用户名
    public static final String  USER = "root";
    //密码
    public static final String  PASSWORD = "root";
    //使用的数据库
    public static final String  DATABASESNAME = "forum";
    //全局静态扫描器,用以读取输入的内容
    static Scanner scanner = new Scanner(System.in);


    /**
     * 主界面
     * @param args
     * @throws SQLException
     */
    public static void main(String[] args){
        try {
            // 连接账户数据库
            Connection userConnection = DriverManager.getConnection("jdbc:mysql://" +HOST+":3306/"+DATABASESNAME,USER,PASSWORD);
            Statement userStatement = userConnection.createStatement();
            //主程序界面
            while (true) {
                System.out.println("==========Home==========");
                System.out.println("1.注册账户");
                System.out.println("2.登录账户");
                System.out.println("3.修改密码");
                System.out.println("4.退出系统");
                String flag = scanner.next();
                switch (flag)
                {
                    case "1":
                        //注册
                        register(userStatement);
                        break;
                    case "2":
                        //登录
                         User user =  signUp(userStatement);
                        //论坛中心
                         ForumCenter(user,userStatement);
                        break;
                    case "3":
                        //更改密码
                        changePassword(userStatement);
                        break;
                    case "4":
                        //关闭连接
                        userStatement.close();
                        userConnection.close();
                        scanner.close();
                        //退出
                        System.out.println("系统退出成功,欢迎下次光临!");
                        return;
                    default:
                        System.out.println("指令错误,重新输入");
                }
            }
        } catch (SQLException e) {
            System.out.println("系统出现异常!");
            throw new RuntimeException(e);
        }

    }


    /**
     * 论坛中心主界面
     * @param user 传入当前登入的账户
     * @param statement 执行SQL语句的对象
     * @throws SQLException
     */
    private static void ForumCenter(User user, Statement statement) throws SQLException {
        //连接论坛数据库
       ResultSet rootResult =   statement.executeQuery("select superUser,superUserPassWord from systemConfig");
      while (rootResult.next())
        {
            if (rootResult.getString("superUser").equals(user.getUserName()))
            {
                //root用户论坛主界面
                while (true) {
                    System.out.println("==========Root管理界面==========");
                    System.out.print("1.查看帖子" +'\t' );
                    System.out.println("2.查看用户" );
                    System.out.print("3.新增帖子"  +'\t');
                    System.out.println("4.新增用户");
                    System.out.print("5.修改帖子" +'\t');
                    System.out.println("6.修改用户");
                    System.out.print("7.删除帖子" +'\t');
                    System.out.println("8.删除用户");
                    System.out.print("9.修改验证码位数" +'\t');
                    System.out.println("10.退出管理" +'\t');
                    String flag = scanner.next();
                    switch (flag)
                    {
                        case "1":
                            //查看帖子
                            showForumCotent(statement);
                            break;
                        case "2":
                            //查看全部用户
                            queryallUser(statement);
                            break;
                        case "3":
                            //新增帖子
                            Posting(user,statement);
                            break;
                        case "4":
                            //新增用户
                            register(statement);
                            break;
                        case "5":
                            //修改帖子
                            modifyCotent(statement,user);
                            break;
                        case "6":
                            //修改用户
                            modifyUser(statement);
                            break;
                        case "7":
                            //删除帖子
                            deleteContent(statement);
                            break;
                        case "8":
                            //删除用户
                            deleteUser(statement);
                            break;
                        case "9":
                            //修改验证码位数
                            setCodeNumber(statement);
                            break;
                        case "10":
                            return;
                        default:
                            System.out.println("指令错误,重新输入");
                    }
                }
            }
            else
            {
                //普通用户论坛主界面
                while (true) {
                    System.out.println("==========论坛中心==========");
                    System.out.println("1.查看论坛");
                    System.out.println("2.发布帖子");
                    System.out.println("3.个人信息");
                    System.out.println("4.修改信息");
                    System.out.println("5.退出论坛");
                    String flag = scanner.next();
                    switch (flag)
                    {
                        case "1":
                            //查看论坛内容
                            showForumCotent(statement);
                            break;
                        case "2":
                            //发帖
                            Posting(user,statement);
                            break;
                        case "3":
                            //查询个人信息
                            queryInformation(user,statement);
                            break;
                        case "4":
                            //修改个人信息
                            alterInformation(user,statement);
                            break;
                        case "5":
                            //退出
                            System.out.println("系统退出成功,欢迎下次光临!");
                            return;
                        default:
                            System.out.println("指令错误,重新输入");
                    }
                }
            }

        }



    }

    /**
     * 修改用户信息
     * @param statement
     */
    private static void modifyUser(Statement statement) throws SQLException {
        //查询全部用户
        queryallUser(statement);
        User user = new User();
        while (true) {
            System.out.println("请输入需要修改的用户名:");
            String name  = scanner.next();
            if (searchUserName(statement,name)  == -1  ) {

                System.out.println("请输入新的用户名:");
                String newName = scanner.next();
                if (user.setUserName(newName) == -1)
                {
                System.out.println("新用户名格式不正确,请重新输入");
                }
                else {
                    System.out.println("请输入新的密码:");
                    String newPassWord = scanner.next();
                    if (user.setPassWord(newPassWord) == -1)
                    {
                        System.out.println("新密码格式不正确,请重新输入");
                    }
                    else
                    {
                        statement.executeUpdate( "update user set passWord = '"+user.getPassWord()+"' where userName = '"+name+"'");
                        statement.executeUpdate( "update user set userName = '"+user.getUserName()+"' where userName = '"+name+"'");
                        System.out.println("用户修改成功!");
                        return;
                    }
                }
            }
            else {
                System.out.println("用户名不存在,请重新输入!");
            }
        }
    }

    /**
     * 修改帖子内容
     * @param statement 执行SQL语句
     * @param user
     * @throws SQLException
     */
    private static void modifyCotent(Statement statement,User user) throws SQLException {
        showForumCotent(statement);
        String title = null;
        String contenttext = null;
        //帖子对象
        Content content = new Content();
        content.setPublisher(user.getUserName());
        while (true) {
            System.out.println("请选择你需要修改的帖子id:");
            int contentId = scanner.nextInt();
            ResultSet contentResult =  statement.executeQuery("select id from data where  id = " + contentId);

            if (contentResult.next())
            {
                while (true) {
                    System.out.println("请输入新的标题[标题需连续不中断,中间不能出现空格]:");
                    title = scanner.next();
                    //承接空格符
                    scanner.nextLine();
                    if (content.setTitle(title) == 1)
                    {
                        break;
                    }
                    else {
                        System.out.println("标题格式有误,请重新输入!");
                    }
                }

                while (true) {
                    System.out.println("请输入新的内容:");
                    contenttext = scanner.nextLine();
                    if (content.setCotent(contenttext) == 1)
                    {
                        break;
                    }
                    else {
                        System.out.println("内容格式有误,请重新输入!");
                    }
                }

                statement.executeUpdate("update data set title = '"+ content.getTitle()+"' where  id = " + contentId);
                statement.executeUpdate("update data set content = '" + content.getCotent()+"' where  id = " + contentId);
                statement.executeUpdate("update data set publisher = '" + content.getPublisher()+"' where  id = " + contentId);
                statement.executeUpdate("update data set publisherId = '" + user.getId() +"' where  id = " + contentId);
                statement.executeUpdate("update data set publishTime = now() where  id = " + contentId);
                System.out.println("修改成功!");
                return;
            }
            else
            {
                System.out.println("不存在该帖子,请重新输入!");
            }
            }

        }

    /**
     * 设置验证码位数
     * @param statement
     */
    private static void setCodeNumber(Statement statement) throws SQLException {
        System.out.println("==========验证码位数设置==========");
        while (true) {
            System.out.println("请输入修改后验证码的个数:");
            int lenth = scanner.nextInt();
            if (lenth >0)
            {
                statement.executeUpdate("update systemConfig  set  codeNumber = "+lenth);
                System.out.println("修改成功!");
                return;
            }
            else {
                System.out.println("验证码位数不正确!请重试!");
            }
        }

    }

    /**
     * 查看普通用户
     * @param statement
     * @throws SQLException
     */
    private static void queryallUser(Statement statement) throws SQLException {
        System.out.println("==========全部用户==========");
        ResultSet allUserResult =  statement.executeQuery("select * from user");
        //获取元数据
        ResultSetMetaData metaData = allUserResult.getMetaData();
        int columnCount = metaData.getColumnCount();
        String[] charName = {"id号", "用户名", "密码", "性别", "年龄", "地址"};
        while (allUserResult.next())
        {
            for (int i = 1; i <= columnCount; i++) {
                System.out.print(charName[i-1] +":"+allUserResult.getObject(i) + "\t");
            }
            System.out.println();
        }
    }

    /**
     * 删除普通用户
     * @param statement
     * @throws SQLException
     */
    private static void deleteUser(Statement statement) throws SQLException {
        queryallUser(statement);
        System.out.println("==========删除用户==========");
        while (true) {
            System.out.println("请您输入需删除的用户id:");
            int id = scanner.nextInt();
            ResultSet idResult =  statement.executeQuery("select id from user where id = "+id);
            if (idResult.next())
            {
                statement.executeUpdate("delete from user where  id = "+id );
                System.out.println("删除成功!");
                return;
            }
            else
            {
                System.out.println("id不存在!请重试!");
            }
        }
    }

    /**
     * 删除帖子
     * @param statement 执行SQL语句的对象
     * @throws SQLException
     */
    private static void deleteContent(Statement statement) throws SQLException {
        showForumCotent(statement);
        System.out.println("==========删除帖子==========");
        while (true) {
            System.out.println("请您输入需删除的帖子id:");
            int id = scanner.nextInt();
            ResultSet idResult =  statement.executeQuery("select id from data where id = "+id);
            if (idResult.next())
            {
                statement.executeUpdate("delete from data where  id = "+id);
                System.out.println("删除成功");
                return;
            }
            else
            {
                System.out.println("id不存在!请重试!");

            }
        }
    }

    /**
     * 进行验证
     */
    public static void  checkCode(Statement statement) throws SQLException {
            while (true) {
                //每次的验证码重新生成
                String code = createCode(statement);
                System.out.println("当前验证码为:" + code);
                System.out.println("请输入验证码:");
                //输入的验证码
                String inputCode = scanner.next();
                if (code.equals(inputCode))
                {
                    //匹配成功,返回
                    return;
                }
                //匹配失败,继续匹配
                System.out.println("验证码错误,请重新输入!");
            }
    }



    /**
     * 获取生成n位数的验证码,并返回
     * @return 验证码
     */
    public static String createCode(Statement statement) throws SQLException {
        //设置默认验证码个数为6位
        int codeNumber = 6;
        ResultSet codeResult =   statement.executeQuery("select codeNumber from systemConfig;");
        if (codeResult.next()) {
            codeNumber = codeResult.getInt("codeNumber");
            Random r = new Random();
            String dict ="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
            String str ="";
            for (int i = 0; i < codeNumber; i++) {
                str += dict.charAt(r.nextInt(62));
            }
            return str;
        }
        else {
            Random r = new Random();
            String dict ="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
            String str ="";
            for (int i = 0; i < codeNumber; i++) {
                str += dict.charAt(r.nextInt(62));
            }
            return str;
        }
    }


    /**
     * 发布帖子
     * @param user 获取当前用户的用户名
     * @param contentStatement 执行SQL语句
     * @throws SQLException
     */
    private static void Posting(User user, Statement contentStatement) throws SQLException {
        String title =null;
        String publisher =null;
        String contenttext =null;
        Content content = new Content();
        System.out.println("==========发布内容==========");
        content.setPublisher(user.getUserName());
        while (true) {
            System.out.println("请输入标题[标题需连续不中断,中间不能出现空格]:");
            title = scanner.next();
            //承接空格符
            scanner.nextLine();
            if (content.setTitle(title) == 1)
            {
                break;
            }
            else {
                System.out.println("标题格式有误,请重新输入!");
            }

        }

        while (true) {
            System.out.println("请输入内容:");
            contenttext = scanner.nextLine();
            if (content.setCotent(contenttext) == 1)
            {
                break;
            }
            else {
                System.out.println("内容格式有误,请重新输入!");
            }

        }

        contentStatement.executeUpdate("insert into data values (null,'"+content.getTitle()+"','"+content.getPublisher()+"',"+user.getId() +",'"+content.getCotent()+"',now())");
        System.out.println("发布成功!");
    }


    /**
     * 修改个人信息
     * @param user 传入当前账户对象
     * @param userStatement 执行SQL语句的对象
     * @throws SQLException
     */
    private static void alterInformation(User user, Statement userStatement) throws SQLException {
        System.out.println("==========修改信息==========");
        while (true) {
            System.out.println("请输入性别:");
            char newgender = scanner.next().charAt(0);
            if (user.setGender(newgender) == 1)
            {
                break;
            }
            else {
                System.out.println("性别格式有误,请重新输入!");
            }
        }
        while (true) {
            System.out.println("请输入年龄:");
            int newage = scanner.nextInt();
            if (user.setAge(newage) == 1)
            {
                break;
            }
            else {
                System.out.println("年龄格式有误,请重新输入!");
            }
        }
        while (true) {
            System.out.println("请输入地址:");
            String newaddress = scanner.next();
            if (user.setAddress(newaddress) == 1)
            {
                break;
            }
            else {
                System.out.println("地址格式有误,请重新输入!");
            }
        }
        //验证码验证
        checkCode(userStatement);
        //修改性别
        userStatement.executeUpdate("update  user  set  gender = '"+user.getGender()+"' where userName = '"+user.getUserName()+"'");
        //修改年龄
        userStatement.executeUpdate("update  user  set  age = "+user.getAge()+" where userName = '"+user.getUserName()+"'");
        //修改地址
        userStatement.executeUpdate("update  user  set  address = '"+user.getAddress()+"' where userName = '"+user.getUserName()+"'");
        System.out.println("恭喜你,修改成功!");
        return;
    }


    /**
     * 查看个人信息
     * @param user 传入的账户
     */
    private static void queryInformation(User user, Statement userStatement) throws SQLException {
        ResultSet resultSet = userStatement.executeQuery("select userName,gender,age,address from user where userName = '"+user.getUserName()+"'");
        System.out.println("==========个人信息==========");
        //获取元数据
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();
        //4.处理数据
        while (resultSet.next())
        {
             String name = resultSet.getString("userName");
            System.out.println("用户名:"+name);
            String gender = resultSet.getString("gender");
            System.out.println("性别:"+gender);
            String age = resultSet.getString("age");
            System.out.println("年龄:"+age);
            String address = resultSet.getString("address");
            System.out.println("地址:"+address);

        }
    }


    /**
     * 查看论坛内容
     * @param statement 执行SQL语句的对象
     * @throws SQLException
     */
    private static void showForumCotent(Statement statement)  throws SQLException {
        //执行查询语句
        ResultSet dataResult =  statement.executeQuery("select * from data");
        System.out.println("==========主界面==========");
        //4.处理数据
        while (dataResult.next())
        {
            String id = dataResult.getString("id");
            System.out.println("----------第"+id+"篇----------");
            String title = dataResult.getString("title");
            System.out.println("标题:"+title);
            String publisher = dataResult.getString("publisher");
            System.out.println("发布者:"+publisher);
            String content = dataResult.getString("content");
            System.out.println("内容:"+content);
            //将时间字符串转换成LocalDateTime对象
            LocalDateTime ldatetime = LocalDateTime.parse(dataResult.getObject("publishTime").toString());
            //设置解析/格式化器
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy年MM月dd日-HH:mm:ss");
            //将LocalDateTime对象按解析器转换成指定格式的字符串
            System.out.println(ldatetime.format(dateTimeFormatter) );
        }

    }


    /**
     * 修改账户密码
     * @param statement 执行SQl语句的对象
     * @throws SQLException 抛出的异常
     */
    private static void changePassword(Statement statement) throws SQLException {
        String userName = null;
        String passWord = null;
        String newPassWord = null;
        while (true) {
            System.out.println("==========修改密码==========");
            System.out.println("请输入需要修改的用户名:");
            userName = scanner.next();
            ResultSet nameresult = statement.executeQuery("select userName from user where userName = '" + userName+"'");
            //判断用户是否存在
            if (nameresult.next())
            {
                System.out.println("请输入原先密码:");
                passWord = scanner.next();
                //存在
                ResultSet passWordResult = statement.executeQuery("select passWord from user where userName = '" + userName+"'");
                //一定进入这个循环
                while (passWordResult.next())
                {
                    //密码匹配成功
                    if (passWordResult.getString("passWord").equals(passWord))
                    {
                        //用户名正确,密码也正确
                        System.out.println("请输入新密码:");
                        newPassWord = scanner.next();
                        statement.executeUpdate("update user set passWord = '"+newPassWord+"' where userName = '" + userName+"'");
                        System.out.println("修改成功!");
                        return;
                    }
                    else
                    {
                        //密码未匹配成功
                        System.out.println("密码错误,请重试!");
                    }
                }
            }
            else{
                //用户不存在
                System.out.println("用户名不存在,请重新输入!");
            }
        }

//
//        if (searchUserName(statement,userName) == 1)
//        {
//            System.out.println("请输入原密码:");
//            String passWord = scanner.next();
//            //如果用户密码存在
//           if (resultSet.next())
//           {
//
//           }
//        }

    }


    /**
     * 查询用户名是否存在
     * @param statement 执行SQL语句的对象
     * @param userName 需要查询的用户名
     * @return 查找是否存在用户,存在则返回-1,不存在则返回1
     * @throws SQLException
     */
    private static int searchUserName(Statement statement,String userName) throws SQLException {
        ResultSet nameResult = statement.executeQuery("select userName from user where userName = '"+userName+"'");
        while (nameResult.next())
        {
            return -1;
        }
        return 1;
    }

    /**
     * 注册功能
     * @param statement 执行SQL语句的对象
     * @throws SQLException
     */
     private static void register(Statement statement) throws SQLException {
        //创建个用户对象
        User user = new User();
        //输入用户名
        while (true) {
            System.out.println("==========注册==========");
            System.out.println("请输入用户名:");
            String userName = scanner.next();
            if (user.setUserName(userName) == -1)
            {
                System.out.println("用户名格式有误,请重新输入!");
            }
            else {
                if (searchUserName(statement,user.getUserName()) == -1)
                {
                    System.out.println("用户名已存在,请重新输入!");
                }
                else {
                    break;
                }

            }
        }

        //输入密码
        while (true) {
            System.out.println("请输入密码:");
            String passWord = scanner.next();
            if (user.setPassWord(passWord) == -1)
            {
                System.out.println("密码格式有误,请重新输入!");
            }
            else {
                break;
            }
        }
        //执行mysql插入语句
        statement.executeUpdate( "insert into user values (null,'"+ user.getUserName()+"','"+user.getPassWord()+"',null,null,null)");
         System.out.println("注册成功!");
     }

    /**
     * 登录功能
     * @param statement 执行SQL语句的对象
     * @return 返回一个账户对象
     * @throws SQLException
     */
    private static User signUp(Statement statement) throws SQLException {
        User user =new User();
         String passWord = null;
        String name = null;
        int id =0;
        while (true) {
            System.out.println("==========登入==========");
            System.out.println("请输入用户名");
            name = scanner.next();
            //查询用户名
            ResultSet nameResult = statement.executeQuery("select id,userName from user where userName = '"+name+"'");
            user.setUserName(name);
            //用户存在
            if (nameResult.next())
            {
                //查询获取的id
                id = nameResult.getInt("id");
                //设置当前用户对象的用户名
                user.setId(id);
                //输入验证码
               checkCode(statement);
                //用户名存在
                System.out.println("请输入密码:");
                passWord = scanner.next();
                //存在
                ResultSet passWordResult = statement.executeQuery("select passWord from user where userName = '" + name+"'");
                //用户存在,密码一定存在
                while (passWordResult.next())
                {
                    if (passWordResult.getString("passWord").equals(passWord))
                    {
                        System.out.println("恭喜"+name+"用户,"+"登入成功!");
                        user.setPassWord(passWord);
                        return user;
                    }
                    else {
                        System.out.println("密码错误,请重试!");
                        break;
                    }
                }
            }
            else {
                System.out.println("用户名不存在,请重新输入!");
            }
        }
    }



}
