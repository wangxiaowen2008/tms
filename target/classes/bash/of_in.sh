#!/bin/bash

# 进入目录
Lv_Path=$1
# 加密文件名
Lv_pwd_fileName=$2
# 数据文件名
Lv_data_fileName=$3
# 私钥文件地址
Lv_privateKey_filePath=$4

# 密码文件
pwdFile=Pw.txt

# 解密密码文件
decrypt_pwdFile=pwd_temp.txt

# 数据文件
dataFile=$Lv_data_fileName

cd $Lv_Path

# 解压密码文件
tar -zxvf $Lv_pwd_fileName

# RSA解密密码文件
openssl pkeyutl -decrypt -inkey $Lv_privateKey_filePath -in $pwdFile -out $decrypt_pwdFile

# 解压,解密
dd if=$dataFile | openssl aes256 -d -kfile $decrypt_pwdFile | tar zxf -

# 删除临时密码文件
rm -rf $decrypt_pwdFile
rm -rf $pwdFile