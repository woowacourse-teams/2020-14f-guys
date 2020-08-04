import { CommonActions } from "@react-navigation/native";

export const navigateWithHistory = (navigation, routes) => {
  navigation.dispatch({
    ...CommonActions.reset({
      index: routes.length,
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
