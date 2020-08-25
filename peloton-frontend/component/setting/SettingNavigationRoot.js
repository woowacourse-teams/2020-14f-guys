import React from "react";
import { createStackNavigator } from "@react-navigation/stack";
import ProfileDetail from "../profile/profiledetail/ProfileDetail";
import ProfileEdit from "../profile/profileedit/ProfileEdit";
import CashUpdate from "../profile/profileedit/CashUpdate";
import NameUpdate from "../profile/profileedit/NameUpdate";
import License from "./license/License";
import Setting from "./Setting";

const SettingStack = createStackNavigator();

const SettingNavigationRoot = () => {
  return (
    <SettingStack.Navigator initialRouteName="Setting">
      <SettingStack.Screen
        name="Setting"
        component={Setting}
        options={{
          title: "설정",
        }}
      />
      <SettingStack.Screen
        name="License"
        component={License}
        options={{ title: "오픈소스 라이선스" }}
      />
    </SettingStack.Navigator>
  );
};

export default SettingNavigationRoot;
