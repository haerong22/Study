import React from 'react';
import { Link } from 'react-router-dom';
import styled from 'styled-components';
import { Navbar, Nav, Form, FormControl, Button } from 'react-bootstrap';

// 하나의 컴포넌트 생성 (재사용)
// 하나의 컴포넌트에 js css를 파일 하나로 관리
const StyledHeaderDiv = styled.div`
  border: 1px solid black;
  height: 300px;
  background-color: ${(props) => props.backgroundColor};
`;

const StyledHeaderLink = styled(Link)`
  color: red;
`;

const Header = () => {
  return (
    <>
      <StyledHeaderDiv backgroundColor="blue">
        <ul>
          <li>
            <StyledHeaderLink to="/">홈</StyledHeaderLink>
          </li>
          <li>
            <StyledHeaderLink to="/login/10">로그인</StyledHeaderLink>
          </li>
        </ul>
      </StyledHeaderDiv>
      <>
        <Navbar bg="dark" variant="dark">
          <Navbar.Brand href="#home">Navbar</Navbar.Brand>
          <Nav className="mr-auto">
            <Link to="/" className="nav-link">
              Home
            </Link>
            <Link to="/login" className="nav-link">
              Login
            </Link>
          </Nav>
          <Form inline>
            <FormControl type="text" placeholder="Search" className="mr-sm-2" />
            <Button variant="outline-info">Search</Button>
          </Form>
        </Navbar>
      </>
    </>
  );
};

export default Header;
