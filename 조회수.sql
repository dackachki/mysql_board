SELECT * FROM ga4DataPagePath

SELECT * FROM article;

SELECT (REPLACE(REPLACE(GA4_PP.pagePathWoQueryStr, "", ""), ".html", "")) AS articleId,
hit
FROM (
    SELECT 
    IF(
        INSTR(GA4_PP.pagePath, '?') = 0,
        GA4_PP.pagePath,
        SUBSTR(GA4_PP.pagePath, 1, INSTR(GA4_PP.pagePath, '?') - 1)
    ) AS pagePathWoQueryStr,
    SUM(GA4_PP.hit) AS hit
    FROM ga4DataPagePath AS GA4_PP
    WHERE GA4_PP.pagePath LIKE 'info/info_%.html%' OR 'IT/IT_%.html%',
    GROUP BY pagePathWoQueryStr
) AS GA4_PP;


SELECT * FROM ga4DataPagePath

DESC ga4DataPagePath
SELECT * FROM ga4DataPagePath

DELETE FROM ga4DataPagePath WHERE pagePath LIKE "%list%";

/*
select if(
length(replace(replace(pagePath,"/IT/IT_",""),".html","")) > 2,
"",
REPLACE(REPLACE(pagePath,"/IT/IT_",""),".html","")) as articleId, hit
from ga4DataPagePath as d where d.pagePath like '%IT%'
union all
SELECT IF(
LENGTH(REPLACE(REPLACE(pagePath,"/info/info_",""),".html","")) > 2,
"",
REPLACE(REPLACE(pagePath,"/info/info_",""),".html","")) AS articleId , hit
FROM ga4DataPagePath AS d WHERE d.pagePath LIKE '%info%' order by articleId
*/


SELECT 

RIGHT(REPLACE(REPLACE(pagePath,"/IT/",""),".html",""),1)
AS articleId, hit
FROM ga4DataPagePath AS d WHERE d.pagePath LIKE '%IT%'

UNION ALL

SELECT 
RIGHT(REPLACE(REPLACE(pagePath,"/info/info_",""),".html",""),1)
AS articleId , hit
FROM ga4DataPagePath AS d WHERE d.pagePath LIKE '%info%' ORDER BY articleId

UPDATE article AS a 
INNER JOIN(
SELECT 

RIGHT(REPLACE(REPLACE(pagePath,"/IT/",""),".html",""),1)
AS articleId, hit
FROM ga4DataPagePath AS d WHERE d.pagePath LIKE '%IT%'

UNION ALL

SELECT 
RIGHT(REPLACE(REPLACE(pagePath,"/info/info_",""),".html",""),1)
AS articleId , hit
FROM ga4DataPagePath AS d WHERE d.pagePath LIKE '%info%' ORDER BY articleId
) AS ga4Data
ON a.id = ga4data.articleId
SET a.hit = ga4data.hit  
SELECT * FROM article
