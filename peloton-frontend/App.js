import React from "react";
import { NavigationContainer } from "@react-navigation/native";
import { StatusBar } from "expo-status-bar";
import { RecoilRoot } from "recoil";
import LoginNavigationRoot from "./component/LoginNavigationRoot";
import * as Linking from "expo-linking";

const prefix = Linking.makeUrl("/");

const linking = {
  prefixes: [prefix, "https://peloton.ga", "peloton://"],
  config: {
    screens: {
      ApplicationNavigationRoot: {
        screens: {
          Home: {
            screens: {
              RaceDeepLinkPage: {
                path: "races/:id",
                exact: true,
              },
            },
          },
        },
      },
      ErrorPage: {
        path: "*",
      },
    },
  },
};

const App = () => {
  return (
    <RecoilRoot>
      <NavigationContainer linking={linking}>
        <StatusBar/>
        <LoginNavigationRoot/>
      </NavigationContainer>
    </RecoilRoot>
  );
};

export default App;
