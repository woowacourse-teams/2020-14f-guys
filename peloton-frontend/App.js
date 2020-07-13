import React from "react";
import { NavigationContainer } from "@react-navigation/native";
import { StatusBar } from "expo-status-bar";

import Root from "./component/Root";

const App = () => (
  <NavigationContainer>
    <StatusBar />
    <Root />
  </NavigationContainer>
);

export default App;
