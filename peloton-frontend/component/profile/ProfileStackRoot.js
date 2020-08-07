import React from "react";
import { createStackNavigator } from "@react-navigation/stack";
import ProfileDetail from "./profiledetail/ProfileDetail";
import ProfileEdit from "./profileedit/ProfileEdit";
import CashUpdate from "./cashupdate/CashUpdate";

const Stack = createStackNavigator();

const ProfileStackRoot = () => {
  return (
    <Stack.Navigator initialRouteName="ProfileDetail">
      <Stack.Screen
        name="ProfileDetail"
        component={ProfileDetail}
        options={{
          title: "Peloton",
        }}
      />
      <Stack.Screen
        name="ProfileEdit"
        component={ProfileEdit}
        options={{
          title: "Settings",
        }}
      />
      <Stack.Screen
        name="CashUpdate"
        component={CashUpdate}
        options={{
          title: "충전하기",
        }}
      />
    </Stack.Navigator>
  );
};

export default ProfileStackRoot;
