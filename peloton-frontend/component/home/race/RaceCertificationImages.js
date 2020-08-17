import React from "react";
import { StyleSheet } from "react-native";
import Swiper from "react-native-swiper";
import RaceCertificationImage from "./RaceCertificationImage";

const RaceCertificationImages = ({ certifications }) => {
  return (
    <Swiper
      style={styles.container}
      loop={true}
      showsPagination={false}
      autoplay
      autoplayTimeout={2.5}
    >
      {certifications && certifications.length > 0 ? (
        certifications.map((item, index) => (
          <RaceCertificationImage key={index} uri={item.image} />
        ))
      ) : (
        <RaceCertificationImage key={0} uri={null} />
      )}
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
