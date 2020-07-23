import React from "react";
import { NavigationContainer } from "@react-navigation/native";
import { StatusBar } from "expo-status-bar";
import { RecoilRoot } from "recoil";

import Root from "./component/Root";

const App = () => (
  <RecoilRoot>
    <NavigationContainer>
      <StatusBar />
      <Root />
    </NavigationContainer>
  </RecoilRoot>
);

export default App;
