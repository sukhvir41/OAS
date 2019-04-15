Create or replace function random_string() returns text as
$$
declare
  chars text[] := '{1,2,3,4,5,6,7,8,9,A,B,C,D,E,F,G,H,J,K,L,M,N,P,Q,R,S,T,U,V,W,X,Y,Z,a,b,c,d,e,f,g,h,j,k,m,n,p,q,r,s,t,u,v,w,x,y,z}';
  result text := '';
  i integer := 0;
begin
  for i in 1..6 loop
    result := result || chars[1+random()*(array_length(chars, 1)-1)];
  end loop;
  return result;
end;
$$ language plpgsql;

CREATE OR REPLACE FUNCTION get_lecture_id()
   RETURNS text AS
$$
DECLARE
   lectureId text := '';
   lectureCount integer := 0;
BEGIN
   
   loop
	select random_string() into lectureId;
	select count(*) into lectureCount from lecture where id = lectureId;
	if lectureCount = 0 then
		exit;		
	end if;
	 RAISE NOTICE ' conflicted on %',lectureId; 	 
   end loop;
   return lectureId;
END;
$$ language 'plpgsql' STRICT;