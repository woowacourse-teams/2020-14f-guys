import React from "react";
import { StyleSheet } from "react-native";
import Swiper from "react-native-swiper";
import RaceCertificationImage from "./RaceCertificationImage";

const RaceCertificationImages = () => {
  return (
    <Swiper
      style={styles.container}
      loop={true}
      showsPagination={false}
      autoplay
      autoplayTimeout={2.5}
    >
      <RaceCertificationImage uri="https://i.pinimg.com/736x/5f/f3/d7/5ff3d71b5834971c30af475c99f67c02.jpg" />
      <RaceCertificationImage uri="https://img1.daumcdn.net/thumb/R800x0/?scode=mtistory2&fname=https%3A%2F%2Ft1.daumcdn.net%2Fcfile%2Ftistory%2F994A57495BB704E012" />
      <RaceCertificationImage uri="https://cdn.pixabay.com/photo/2019/08/01/12/36/illustration-4377408_960_720.png" />
    </Swiper>
  );
};

const styles = StyleSheet.create({
  container: {
    height: 600,
  },
  image: {
    flex: 1,
    resizeMode: "cover",
  },
});

export default RaceCertificationImages;
