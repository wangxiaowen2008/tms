/**
 * 支付功能JavaScript
 * 实现支付流程的交互逻辑
 */

// 全局变量
let currentStep = 1;
let totalSteps = 4;
let selectedPaymentMethod = 'alipay';
let countdownTimer = null;
let paymentStatusCheckInterval = null;
let orderData = {
    orderNumber: 'TMS20241201001',
    orderTime: '2024-12-01 10:30:00',
    productName: '运输管理系统服务',
    servicePeriod: '1个月',
    unitPrice: 1000.00,
    totalAmount: 1000.00
};

// DOM加载完成后初始化
document.addEventListener('DOMContentLoaded', function() {
    initializePayment();
    bindEvents();
});

/**
 * 初始化支付功能
 */
function initializePayment() {
    // 初始化订单数据
    updateOrderDisplay();
    
    // 设置默认支付方式
    updatePaymentMethodDisplay();
    
    console.log('支付功能初始化完成');
}

/**
 * 绑定事件监听器
 */
function bindEvents() {
    // 支付方式选择事件
    document.querySelectorAll('.payment-method').forEach(method => {
        method.addEventListener('click', function() {
            const methodType = this.getAttribute('data-method');
            selectPaymentMethod(methodType);
        });
    });
    
    // 支付方式单选按钮事件
    document.querySelectorAll('input[name="paymentMethod"]').forEach(radio => {
        radio.addEventListener('change', function() {
            selectedPaymentMethod = this.value;
            updatePaymentMethodDisplay();
        });
    });
}

/**
 * 更新订单显示信息
 */
function updateOrderDisplay() {
    document.getElementById('orderNumber').textContent = orderData.orderNumber;
    document.getElementById('orderTime').textContent = orderData.orderTime;
    document.getElementById('productName').textContent = orderData.productName;
    document.getElementById('servicePeriod').textContent = orderData.servicePeriod;
    document.getElementById('unitPrice').textContent = `¥${orderData.unitPrice.toFixed(2)}`;
    document.getElementById('totalAmount').textContent = `¥${orderData.totalAmount.toFixed(2)}`;
}

/**
 * 选择支付方式
 * @param {string} method - 支付方式
 */
function selectPaymentMethod(method) {
    selectedPaymentMethod = method;
    
    // 更新单选按钮
    document.querySelector(`input[value="${method}"]`).checked = true;
    
    // 更新支付方式显示
    updatePaymentMethodDisplay();
    
    // 更新支付方式选择样式
    document.querySelectorAll('.payment-method').forEach(el => {
        el.classList.remove('selected');
    });
    document.querySelector(`[data-method="${method}"]`).classList.add('selected');
}

/**
 * 更新支付方式显示
 */
function updatePaymentMethodDisplay() {
    const methodNames = {
        'alipay': '支付宝',
        'wechat': '微信支付',
        'bank': '银行转账'
    };
    
    document.getElementById('payMethod').textContent = methodNames[selectedPaymentMethod];
    document.getElementById('resultMethod').textContent = methodNames[selectedPaymentMethod];
}

/**
 * 下一步操作
 */
function nextStep() {
    if (currentStep < totalSteps) {
        // 验证当前步骤
        if (!validateCurrentStep()) {
            return;
        }
        
        // 执行当前步骤的特殊逻辑
        executeStepLogic(currentStep);
        
        // 进入下一步
        currentStep++;
        updateStepDisplay();
        
        // 如果是支付验证步骤，开始倒计时和状态检查
        if (currentStep === 3) {
            startPaymentProcess();
        }
    }
}

/**
 * 上一步操作
 */
function prevStep() {
    if (currentStep > 1) {
        // 停止支付相关的定时器
        if (currentStep === 3) {
            stopPaymentProcess();
        }
        
        currentStep--;
        updateStepDisplay();
    }
}

/**
 * 更新步骤显示
 */
function updateStepDisplay() {
    // 更新步骤指示器
    document.querySelectorAll('.step').forEach((step, index) => {
        const stepNumber = index + 1;
        step.classList.remove('active', 'completed');
        
        if (stepNumber < currentStep) {
            step.classList.add('completed');
        } else if (stepNumber === currentStep) {
            step.classList.add('active');
        }
    });
    
    // 更新步骤内容
    document.querySelectorAll('.step-content').forEach((content, index) => {
        const stepNumber = index + 1;
        content.classList.remove('active');
        
        if (stepNumber === currentStep) {
            content.classList.add('active');
        }
    });
}

/**
 * 验证当前步骤
 * @returns {boolean} 验证结果
 */
function validateCurrentStep() {
    switch (currentStep) {
        case 1:
            // 订单确认步骤，无需特殊验证
            return true;
        case 2:
            // 支付方式选择步骤
            if (!selectedPaymentMethod) {
                showToast('请选择支付方式', 'warning');
                return false;
            }
            return true;
        case 3:
            // 支付验证步骤
            return true;
        default:
            return true;
    }
}

/**
 * 执行步骤特殊逻辑
 * @param {number} step - 步骤号
 */
function executeStepLogic(step) {
    switch (step) {
        case 1:
            // 订单确认逻辑
            console.log('订单已确认');
            break;
        case 2:
            // 支付方式确认逻辑
            console.log(`已选择支付方式: ${selectedPaymentMethod}`);
            break;
        case 3:
            // 支付验证逻辑
            console.log('开始支付验证');
            break;
    }
}

/**
 * 开始支付流程
 */
function startPaymentProcess() {
    // 生成支付二维码（模拟）
    generatePaymentQRCode();
    
    // 开始倒计时
    startCountdown();
    
    // 开始支付状态检查
    startPaymentStatusCheck();
    
    showToast('支付二维码已生成，请扫码支付', 'success');
}

/**
 * 生成支付二维码
 */
function generatePaymentQRCode() {
    const qrCodeContainer = document.querySelector('.qr-code');
    const qrCodeIcon = qrCodeContainer.querySelector('i');
    
    // 模拟生成二维码的过程
    qrCodeIcon.style.color = '#4CAF50';
    qrCodeIcon.className = 'fas fa-qrcode';
    
    // 这里可以集成真实的二维码生成库
    // 例如：QRCode.js 或 qrcode.js
    console.log('支付二维码已生成');
}

/**
 * 开始倒计时
 */
function startCountdown() {
    let timeLeft = 5 * 60; // 5分钟
    
    const updateCountdown = () => {
        const minutes = Math.floor(timeLeft / 60);
        const seconds = timeLeft % 60;
        
        document.getElementById('countdown').textContent = 
            `${minutes.toString().padStart(2, '0')}:${seconds.toString().padStart(2, '0')}`;
        
        if (timeLeft <= 0) {
            clearInterval(countdownTimer);
            showToast('支付超时，请重新发起支付', 'error');
            // 可以在这里跳转到支付失败页面或重新开始
        }
        
        timeLeft--;
    };
    
    updateCountdown();
    countdownTimer = setInterval(updateCountdown, 1000);
}

/**
 * 开始支付状态检查
 */
function startPaymentStatusCheck() {
    paymentStatusCheckInterval = setInterval(() => {
        checkPaymentStatus();
    }, 3000); // 每3秒检查一次
}

/**
 * 检查支付状态
 */
function checkPaymentStatus() {
    // 模拟支付状态检查
    // 这里应该调用后端API检查真实的支付状态
    const mockPaymentStatus = Math.random() > 0.8; // 20%概率支付成功
    
    if (mockPaymentStatus) {
        stopPaymentProcess();
        showToast('支付成功！', 'success');
        setTimeout(() => {
            nextStep(); // 进入支付完成步骤
        }, 1000);
    }
}

/**
 * 停止支付流程
 */
function stopPaymentProcess() {
    if (countdownTimer) {
        clearInterval(countdownTimer);
        countdownTimer = null;
    }
    
    if (paymentStatusCheckInterval) {
        clearInterval(paymentStatusCheckInterval);
        paymentStatusCheckInterval = null;
    }
}

/**
 * 手动检查支付状态
 */
function checkPaymentStatus() {
    showLoading(true);
    
    // 模拟API调用
    setTimeout(() => {
        showLoading(false);
        
        // 模拟支付成功
        const isSuccess = Math.random() > 0.5;
        
        if (isSuccess) {
            stopPaymentProcess();
            showToast('支付成功！', 'success');
            setTimeout(() => {
                nextStep();
            }, 1000);
        } else {
            showToast('支付尚未完成，请继续等待', 'warning');
        }
    }, 2000);
}

/**
 * 返回操作
 */
function goBack() {
    // 这里可以跳转到订单列表或其他页面
    showToast('返回上一页面', 'info');
    console.log('返回操作');
}

/**
 * 返回首页
 */
function goToHome() {
    // 跳转到系统首页
    showToast('正在跳转到首页...', 'info');
    setTimeout(() => {
        window.location.href = '/'; // 根据实际路由调整
    }, 1000);
}

/**
 * 下载收据
 */
function downloadReceipt() {
    showLoading(true);
    
    // 模拟下载过程
    setTimeout(() => {
        showLoading(false);
        
        // 创建收据内容
        const receiptContent = `
            收据
            ====================
            订单号: ${orderData.orderNumber}
            支付金额: ¥${orderData.totalAmount.toFixed(2)}
            支付时间: ${new Date().toLocaleString()}
            支付方式: ${document.getElementById('resultMethod').textContent}
            ====================
        `;
        
        // 创建下载链接
        const blob = new Blob([receiptContent], { type: 'text/plain' });
        const url = URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.href = url;
        a.download = `收据_${orderData.orderNumber}.txt`;
        document.body.appendChild(a);
        a.click();
        document.body.removeChild(a);
        URL.revokeObjectURL(url);
        
        showToast('收据下载完成', 'success');
    }, 2000);
}

/**
 * 显示加载遮罩
 * @param {boolean} show - 是否显示
 */
function showLoading(show) {
    const overlay = document.getElementById('loadingOverlay');
    if (show) {
        overlay.classList.add('show');
    } else {
        overlay.classList.remove('show');
    }
}

/**
 * 显示提示信息
 * @param {string} message - 提示信息
 * @param {string} type - 提示类型 (success, error, warning, info)
 */
function showToast(message, type = 'info') {
    const toast = document.getElementById('toast');
    
    // 设置提示内容和样式
    toast.textContent = message;
    toast.className = `toast ${type}`;
    
    // 显示提示
    toast.classList.add('show');
    
    // 3秒后自动隐藏
    setTimeout(() => {
        toast.classList.remove('show');
    }, 3000);
    
    console.log(`Toast: ${message} (${type})`);
}

/**
 * 格式化金额
 * @param {number} amount - 金额
 * @returns {string} 格式化后的金额
 */
function formatAmount(amount) {
    return `¥${amount.toFixed(2)}`;
}

/**
 * 格式化时间
 * @param {Date} date - 日期对象
 * @returns {string} 格式化后的时间
 */
function formatDateTime(date) {
    return date.toLocaleString('zh-CN', {
        year: 'numeric',
        month: '2-digit',
        day: '2-digit',
        hour: '2-digit',
        minute: '2-digit',
        second: '2-digit'
    });
}

/**
 * 生成订单号
 * @returns {string} 订单号
 */
function generateOrderNumber() {
    const timestamp = Date.now();
    const random = Math.floor(Math.random() * 1000);
    return `TMS${timestamp}${random.toString().padStart(3, '0')}`;
}

/**
 * 验证支付参数
 * @param {Object} params - 支付参数
 * @returns {boolean} 验证结果
 */
function validatePaymentParams(params) {
    const requiredFields = ['orderNumber', 'amount', 'paymentMethod'];
    
    for (const field of requiredFields) {
        if (!params[field]) {
            showToast(`缺少必要参数: ${field}`, 'error');
            return false;
        }
    }
    
    if (params.amount <= 0) {
        showToast('支付金额必须大于0', 'error');
        return false;
    }
    
    return true;
}

// 导出函数供其他模块使用
window.PaymentModule = {
    nextStep,
    prevStep,
    selectPaymentMethod,
    checkPaymentStatus,
    goBack,
    goToHome,
    downloadReceipt,
    showToast,
    formatAmount,
    formatDateTime,
    generateOrderNumber,
    validatePaymentParams
}; 