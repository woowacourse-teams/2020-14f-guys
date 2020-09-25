import React, { useEffect } from "react";
import { NavigationContainer } from "@react-navigation/native";
import { StatusBar } from "expo-status-bar";
import { RecoilRoot } from "recoil";
import LoginNavigationRoot from "./component/login/LoginNavigationRoot";

import { DEEP_LINK_BASE_URL } from "./utils/constants";
import { config } from "./config/config";
import * as Amplitude from "expo-analytics-amplitude";

const apiKey = config.apiKey;

const linking = {
  prefixes: [DEEP_LINK_BASE_URL],
  config: {
    screens: {
      ApplicationNavigationRoot: {
        screens: {
          Home: {
            path: "home",
            screens: {
              RaceDeepLinkPage: {
                path: "races/:id",
              },
              RaceDetail: {
                path: "races/detail/:id",
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
  useEffect(() => {
    Amplitude.initialize(apiKey);
    Amplitude.setTrackingOptions({
      disableCarrier: true,
      disableRegion: true,
      disableCity: true,
      disableCountry: true,
      disableLatLng: true,
      disableDMA: true,
      disableIDFA: true,
      disableIDFV: true,
      disableIPAddress: true,
    });
  }, []);

  return (
    <RecoilRoot>
      <NavigationContainer linking={linking}>
        <StatusBar />
        <LoginNavigationRoot />
      </NavigationContainer>
    </RecoilRoot>
  );
};

export default App;
