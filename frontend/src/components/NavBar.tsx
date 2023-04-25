import React, { useState } from "react";
import { NavLink } from "react-router-dom";
import styled from "styled-components";
import { useDispatch, useSelector } from "react-redux";

import { RootState } from "../_store/store";
import { toggleTheme } from "../_store/slices/themeSlice";
import {DarkMode, LightMode} from "@mui/icons-material";
import LogoutIcon from '@mui/icons-material/Logout';
import {Switch} from "@mui/joy";

type ToggleThemeProps = {
  isDark: boolean;
  setIsDark: (theme: boolean) => void;
  dispatch: (toggleTheme: any) => void;
};

/** 테마 변경, 추후 toggle 버튼으로 바꿀 예정 */
function handleToggleTheme({ isDark, setIsDark, dispatch }: ToggleThemeProps) {
  if (isDark) {
    document.documentElement.setAttribute("data-theme", "light");
    dispatch(toggleTheme("light"));
    setIsDark(false);
  } else {
    document.documentElement.setAttribute("data-theme", "dark");
    dispatch(toggleTheme("dark"));
    setIsDark(true);
  }
}

const Nav = styled.nav`
  height: var(--nav-height);
  display: flex;
  flex-shrink: 0;
  align-items: center;
  justify-content: space-between;
  padding-left: 30px;
  padding-right: 30px;
  font-size: 18px;
  border-bottom: 1px solid var(--emphasize-color);
  & a {
    margin-right: 30px;
    font-weight: bold;
    &.active {
      color: var(--emphasize-color);
    }
  }
  
  //& button {
  //  background-color: transparent;
  //  color: var(--check-color);
  //  font-size: 20px;
  //}
`;

const NavLeftDiv = styled.div``

const NavRightDiv = styled.div`
  display: flex;
  flex-direction: row;
  align-items: center;
  & > * {
    margin-right: 30px;
  }
  & > *:last-child {
    margin-right: 0;
  }
`

const Profile = styled.div`
  font-weight: bold;
`

const LogoutButton = styled.button`
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: transparent;
  color: var(--emphasize-color);
`

function NavBar() {
  let [isDark, setIsDark] = useState(useSelector((state: RootState) => state.theme.theme === "dark"))
  const dispatch = useDispatch();

  return (
    <Nav>
      <NavLeftDiv>
        <NavLink className={({isActive}) => isActive? "active" : ""} to="/">대시보드</NavLink>
        <NavLink to="/report">레포트</NavLink>
        <NavLink to="/transfer">전이학습</NavLink>
        <NavLink to="/login">로그인</NavLink>
      </NavLeftDiv>

      <NavRightDiv>
        <Profile>나동빈 <span style={{fontSize: "15px", fontWeight: "normal"}}>님</span></Profile>
        <Switch
          checked={isDark}
          onChange={(): void => handleToggleTheme({ isDark, setIsDark, dispatch })}
          slotProps={{
            input: { 'aria-label': 'Dark mode' },
            thumb: {
              children: isDark? <DarkMode sx={{fontSize: '20px'}} /> : <LightMode sx={{fontSize: '20px'}} />,
            },
          }}
          size="sm"
          color={isDark ? 'primary' : 'neutral'}
          sx={{
            '--Switch-thumbSize': '25px',
          }}
        />
        <LogoutButton aria-label="logout"><LogoutIcon  /></LogoutButton>
      </NavRightDiv>
      {/*<input type="checkbox" checked={isDark} onChange={(): void => handleToggleTheme({ isDark, setIsDark, dispatch })} />*/}
    </Nav>
  );
}

export default NavBar;
