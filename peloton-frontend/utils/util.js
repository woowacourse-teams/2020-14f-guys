import { CommonActions, StackActions, TabActions } from "@react-navigation/native";

export const navigateWithHistory = (navigation, routes) => {
  navigation.dispatch({
    ...CommonActions.reset({
      index: routes.length - 1,
      routes: routes,
    }),
  });
};

export const navigateWithoutHistory = (navigation, name) => {
  navigation.dispatch({
    ...CommonActions.reset({
      index: 0,
      routes: [
        {
          name: name,
        },
      ],
    }),
  });
};

export const navigateTabScreen = (navigation, name) => {
  navigation.dispatch(TabActions.jumpTo(name));
};
