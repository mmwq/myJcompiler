# myJcompiler
It's a compiler designed by me!

here is an example program:

main
{
int a[2]
 }
begin
a[0] := 10;
a[1] := a[0]+5;
if  a[1] > 11 then
	if a[1] > 14 then
		a[1]:=a[1]+1;	
	else 
		a[1]:=a[1]-1;
	fi;
fi;
write a[1];
end

NOTE: my language only deal with INTEGERS

A program must start with main followed by decalrations in curly bracket,

varible declaration rules:

  a. you can decalre more than one varibles in a line; example: int a, b, c, d ,e;
  
  b. you can decalre a varible and initialize it,but only one at a line; example : int a := 5;
  
  c. to declare a array is just like in C : int a [10];
  
  d. declaration in last line DON't need to end with semicolon 
  
then  write begin and write your codes following rules below

  a: use := instead of = to assignment 
  
   写英文太累了。。就此打住
   
  b: if语句中的判断条件 限于 表达式 关系 表达式 关系包括 > < == != 表达式可以是变量或数字，也可运算。
  
  d: if语句结构：if 表达式 关系 表达式 then 语句 fi; 或者 if 表达式 关系 表达式 then 语句 else 语句 fi;
     fi代表结束if， 语句可以为空
     
  c: write 仅限于输出变量，不能输出字符串或者字符，多个write不会加空格 = = 
  
  e: 注意每个语句后都要有分号，包括if（也就是fi后面有分号）
  
  f: 程序以end结束
  
  打包成jar包之后需要带上LALR分析表才能运行工作 
  运行 命令：java -jar jcompiler.jar sourcefilename aimfilename
  sourcefilename是源文件名(1.txt)
  aimfilename 是目标文件名(2.asm)
  
  生成的 2.asm 就可以通过masm运行了

