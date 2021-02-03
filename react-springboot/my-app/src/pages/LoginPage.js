import React from 'react';
import Footer from '../components/Footer';
import Header from '../components/Header';
import Login from '../components/Login';
import { Title } from '../Style';

const LoginPage = () => {
  return (
    <div>
      <Header />
      <Title>Hello</Title>
      <Login />
      <Footer />
    </div>
  );
};

export default LoginPage;
