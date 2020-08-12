import React from "react";
import { NavigationContainer } from "@react-navigation/native";
import { StatusBar } from "expo-status-bar";
import { RecoilRoot } from "recoil";
import LoginNavigationRoot from "./component/LoginNavigationRoot";
import { DEEP_LINK_BASE_URL } from "./utils/constants";

const linking = {
  prefixes: [DEEP_LINK_BASE_URL],
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
