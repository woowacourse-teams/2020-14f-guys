export const DateFormatter = {
  yyyyMMdd: (date) => {
    const MM =
      date.getMonth() + 1 >= 10
        ? date.getMonth() + 1
        : `0${date.getMonth() + 1}`;
    const dd = date.getDate() >= 10 ? date.getDate() : `0${date.getDate()}`;
    return `${date.getFullYear()}-${MM}-${dd}`;
  },
  UTCyyyyMMdd: (date) => date.toISOString().split("T")[0],
};
