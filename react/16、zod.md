pnpm install zod react

UserRegistrationForm.tsx
``` ts
import React, { useState } from 'react';
import { z } from 'zod';

// 1. 定义表单验证的 Zod Schema
const RegistrationSchema = z.object({
  // 用户名：3-20个字符，不能包含空格
  username: z.string()
    .min(3, "用户名至少需要3个字符")
    .max(20, "用户名不能超过20个字符")
    .regex(/^[^\s]+$/, "用户名不能包含空格")
    .trim(),  // 自动去除首尾空格
  
  // 邮箱：必须是有效的邮箱格式
  email: z.string()
    .email("请输入有效的邮箱地址")
    .toLowerCase(),  // 转换为小写
  
  // 年龄：可选，但如果提供必须≥18
  age: z.number()
    .int("年龄必须是整数")
    .min(18, "必须年满18岁")
    .optional()
    .nullable(),  // 允许为 null
  
  // 密码：至少8个字符，包含至少一个大写字母和一个数字
  password: z.string()
    .min(8, "密码至少需要8个字符")
    .regex(/[A-Z]/, "密码必须包含至少一个大写字母")
    .regex(/[0-9]/, "密码必须包含至少一个数字"),
  
  // 确认密码：必须与密码一致
  confirmPassword: z.string()
}).refine(
  // 跨字段验证：确认密码必须与密码一致
  (data) => data.password === data.confirmPassword,
  {
    message: "两次输入的密码不一致",
    path: ["confirmPassword"]  // 错误信息关联到 confirmPassword 字段
  }
);

// 从 Zod Schema 推导 TypeScript 类型
type RegistrationFormData = z.infer<typeof RegistrationSchema>;

// 错误信息类型
type FormErrors = Partial<Record<keyof RegistrationFormData, string>>;

const UserRegistrationForm = () => {
  // 2. 表单状态管理
  const [formData, setFormData] = useState<RegistrationFormData>({
    username: '',
    email: '',
    age: null,
    password: '',
    confirmPassword: ''
  });
  
  // 错误信息状态
  const [errors, setErrors] = useState<FormErrors>({});
  
  // 提交状态
  const [isSubmitting, setIsSubmitting] = useState<boolean>(false);
  
  // 提交成功状态
  const [submitSuccess, setSubmitSuccess] = useState<boolean>(false);

  // 3. 处理表单输入变化
  const handleInputChange = (
    e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>
  ) => {
    const { name, value, type } = e.target;
    
    // 处理不同类型的输入
    let processedValue: string | number | null = value;
    if (type === 'number') {
      processedValue = value === '' ? null : Number(value);
    }
    
    // 更新表单数据
    setFormData(prev => ({
      ...prev,
      [name]: processedValue
    }));
    
    // 清除对应字段的错误（当用户开始输入时）
    if (errors[name as keyof RegistrationFormData]) {
      setErrors(prev => ({
        ...prev,
        [name]: undefined
      }));
    }
  };

  // 4. 表单提交处理
  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setIsSubmitting(true);
    setErrors({});
    setSubmitSuccess(false);
    
    // 模拟网络延迟
    await new Promise(resolve => setTimeout(resolve, 800));
    
    // 使用 Zod 验证表单数据
    const result = RegistrationSchema.safeParse(formData);
    
    if (!result.success) {
      // 处理验证错误
      const newErrors: FormErrors = {};
      
      // 提取所有错误信息
      result.error.issues.forEach(issue => {
        const field = issue.path[0] as keyof RegistrationFormData;
        newErrors[field] = issue.message;
      });
      
      setErrors(newErrors);
      setIsSubmitting(false);
      return;
    }
    
    // 验证成功，处理表单数据（实际应用中会发送到服务器）
    console.log('表单验证成功，提交的数据：', result.data);
    
    // 模拟提交成功
    setSubmitSuccess(true);
    setIsSubmitting(false);
    
    // 重置表单
    setTimeout(() => {
      setFormData({
        username: '',
        email: '',
        age: null,
        password: '',
        confirmPassword: ''
      });
      setSubmitSuccess(false);
    }, 3000);
  };

  // 5. 渲染表单
  return (
    <div className="max-w-md mx-auto p-6 bg-white rounded-lg shadow-md">
      <h2 className="text-2xl font-bold mb-6 text-gray-800">用户注册</h2>
      
      {submitSuccess ? (
        <div className="p-4 bg-green-100 text-green-800 rounded-md">
          注册成功！表单将在3秒后重置。
        </div>
      ) : (
        <form onSubmit={handleSubmit} className="space-y-4">
          {/* 用户名输入 */}
          <div>
            <label htmlFor="username" className="block text-sm font-medium text-gray-700 mb-1">
              用户名 *
            </label>
            <input
              type="text"
              id="username"
              name="username"
              value={formData.username}
              onChange={handleInputChange}
              className={`w-full px-3 py-2 border ${
                errors.username ? 'border-red-500' : 'border-gray-300'
              } rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500`}
              placeholder="请输入用户名"
            />
            {errors.username && (
              <p className="mt-1 text-sm text-red-600">{errors.username}</p>
            )}
          </div>
          
          {/* 邮箱输入 */}
          <div>
            <label htmlFor="email" className="block text-sm font-medium text-gray-700 mb-1">
              邮箱 *
            </label>
            <input
              type="email"
              id="email"
              name="email"
              value={formData.email}
              onChange={handleInputChange}
              className={`w-full px-3 py-2 border ${
                errors.email ? 'border-red-500' : 'border-gray-300'
              } rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500`}
              placeholder="your@email.com"
            />
            {errors.email && (
              <p className="mt-1 text-sm text-red-600">{errors.email}</p>
            )}
          </div>
          
          {/* 年龄输入 */}
          <div>
            <label htmlFor="age" className="block text-sm font-medium text-gray-700 mb-1">
              年龄
            </label>
            <input
              type="number"
              id="age"
              name="age"
              value={formData.age || ''}
              onChange={handleInputChange}
              className={`w-full px-3 py-2 border ${
                errors.age ? 'border-red-500' : 'border-gray-300'
              } rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500`}
              placeholder="请输入年龄（选填）"
            />
            {errors.age && (
              <p className="mt-1 text-sm text-red-600">{errors.age}</p>
            )}
          </div>
          
          {/* 密码输入 */}
          <div>
            <label htmlFor="password" className="block text-sm font-medium text-gray-700 mb-1">
              密码 *
            </label>
            <input
              type="password"
              id="password"
              name="password"
              value={formData.password}
              onChange={handleInputChange}
              className={`w-full px-3 py-2 border ${
                errors.password ? 'border-red-500' : 'border-gray-300'
              } rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500`}
              placeholder="请输入密码"
            />
            {errors.password && (
              <p className="mt-1 text-sm text-red-600">{errors.password}</p>
            )}
            <p className="mt-1 text-xs text-gray-500">
              密码至少8个字符，包含至少一个大写字母和一个数字
            </p>
          </div>
          
          {/* 确认密码输入 */}
          <div>
            <label htmlFor="confirmPassword" className="block text-sm font-medium text-gray-700 mb-1">
              确认密码 *
            </label>
            <input
              type="password"
              id="confirmPassword"
              name="confirmPassword"
              value={formData.confirmPassword}
              onChange={handleInputChange}
              className={`w-full px-3 py-2 border ${
                errors.confirmPassword ? 'border-red-500' : 'border-gray-300'
              } rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500`}
              placeholder="请再次输入密码"
            />
            {errors.confirmPassword && (
              <p className="mt-1 text-sm text-red-600">{errors.confirmPassword}</p>
            )}
          </div>
          
          {/* 提交按钮 */}
          <button
            type="submit"
            disabled={isSubmitting}
            className="w-full bg-blue-600 text-white py-2 px-4 rounded-md hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-offset-2 disabled:bg-blue-400 disabled:cursor-not-allowed"
          >
            {isSubmitting ? '注册中...' : '注册'}
          </button>
        </form>
      )}
    </div>
  );
};

export default UserRegistrationForm;

```

App.tsx
``` ts
import './App.css'
import UserRegistrationForm from './component/UserRegistrationForm';


function App() {
  return (
    <>
      <UserRegistrationForm />
    </>
  );
}

export default App

```
