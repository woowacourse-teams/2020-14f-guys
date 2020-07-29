import React from "react";
import { NavigationContainer } from "@react-navigation/native";
import { StatusBar } from "expo-status-bar";
import { RecoilRoot } from "recoil";
import LoginNavigationRoot from "./component/login/LoginNavigationRoot";

const App = () => (
  <RecoilRoot>
    <NavigationContainer>
      <StatusBar />
      <LoginNavigationRoot />
    </NavigationContainer>
  </RecoilRoot>
);

export default App;
