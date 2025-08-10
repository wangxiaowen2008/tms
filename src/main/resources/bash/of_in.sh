#!/bin/bash

# 文件目录
Lv_Path=$1
# 密码文件名称
Lv_pwd_fileName=$2
# 加密压缩文件名称
Lv_data_fileName=$3
# 私钥文件地址
Lv_privateKey_filePath=$4
# 解密后的文件名称
Lv_output_fileName=$5

# 密码文件
pwdFile=$Lv_pwd_fileName

# 加密压缩文件
dataFile=$Lv_data_fileName

# 解密后的密码文件
decrypt_pwdFile=Pw_decrypt.txt

cd $Lv_Path

# RSA解密密码文件
openssl pkeyutl -decrypt -inkey $Lv_privateKey_filePath -in $pwdFile -out $decrypt_pwdFile

# 解密并解压缩数据文件
# 分为两步以提高稳定性：1. 解密到临时文件 2. 从临时文件解压
Lv_temp_archive="decrypted_temp.tar.gz"
dd if=$dataFile 2>/dev/null | openssl des3 -d -salt -kfile $decrypt_pwdFile > $Lv_temp_archive

# 从临时压缩包解压，-v参数会显示解压的文件名
tar -zxvf $Lv_temp_archive

# 如果指定了输出文件名，则重命名解压后的文件
if [ ! -z "$Lv_output_fileName" ]; then
    # 获取压缩包内的文件名 (假设只有一个文件)
    EXTRACTED_FILE=$(tar -tf $Lv_temp_archive | head -n 1)
    if [ ! -z "$EXTRACTED_FILE" ] && [ -f "$EXTRACTED_FILE" ]; then
        mv "$EXTRACTED_FILE" "$Lv_output_fileName"
    fi
fi

# 删除解密后的密码文件和临时压缩包
rm -rf $decrypt_pwdFile
rm -rf $Lv_temp_archive