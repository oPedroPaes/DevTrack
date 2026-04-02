import type { ReactNode, CSSProperties } from "react";

type CardProps = {
  title?: string;
  value?: string;
  children?: ReactNode;
};

export default function Card({ title, value, children }: CardProps) {
  return (
    <div style={cardStyle}>
      {title && <h3>{title}</h3>}
      {value && <p>{value}</p>}
      {children}
    </div>
  );
}

const cardStyle: CSSProperties = {
  padding: "20px",
  border: "2px solid #ccc",
  borderRadius: "10px",
};
