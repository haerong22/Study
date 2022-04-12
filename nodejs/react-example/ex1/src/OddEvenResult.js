const OddEvenResult = ({count}) => {
  return (
    <div>
      {count % 2 === 0 ? "짝수" : "홀수"}
    </div>
  )
}

export default OddEvenResult;