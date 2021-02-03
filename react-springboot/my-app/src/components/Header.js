import React from 'react';
import styled from 'styled-components';

// 하나의 컴포넌트 생성 (재사용)
// 하나의 컴포넌트에 js css를 파일 하나로 관리
const HeaderList = styled.div`
  border: 1px solid black;
  height: 300px;
`;

const Header = () => {
  return (
    <HeaderList>
      <ul>
        <li>메뉴 1</li>
        <li>메뉴 2</li>
      </ul>
    </HeaderList>
  );
};

export default Header;
