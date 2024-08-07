import React from 'react';
import styled from 'styled-components';

// 하나의 컴포넌트 생성 (재사용)
// 하나의 컴포넌트에 js css를 파일 하나로 관리
const StyledFooterDiv = styled.div`
  border: 1px solid black;
  height: 300px;
`;

const Footer = () => {
  return (
    <StyledFooterDiv>
      <ul>
        <li>오시는길 : 서울</li>
        <li>전화번호 : 021321231</li>
      </ul>
    </StyledFooterDiv>
  );
};

export default Footer;
