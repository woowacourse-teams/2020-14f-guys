import { CommonActions } from "@react-navigation/native";

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
