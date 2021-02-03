import React from 'react';
import { Link } from 'react-router-dom';
import styled from 'styled-components';

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
  );
};

export default Header;
