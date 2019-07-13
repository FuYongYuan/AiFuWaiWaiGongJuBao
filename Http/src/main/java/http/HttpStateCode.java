package http;

import enumerate.HttpState;

import java.util.TreeMap;

/**
 * Html状态码
 */
public class HttpStateCode {

    public static TreeMap<Integer, String> state = new TreeMap<>();

    static {
        //http状态返回代码 1xx（临时响应）表示临时响应并需要请求者继续执行操作的状态代码。
        state.put(100, "（继续）请求者应当继续提出请求。 服务器返回此代码表示已收到请求的第一部分，正在等待其余部分。");
        state.put(101, "（切换协议）请求者已要求服务器切换协议，服务器已确认并准备切换。");
        //http状态返回代码 2xx （成功）表示成功处理了请求的状态代码。
        state.put(200, "（成功）服务器已成功处理了请求。 通常，这表示服务器提供了请求的网页。");
        state.put(201, "（已创建）请求成功并且服务器创建了新的资源。");
        state.put(202, "（已接受）服务器已接受请求，但尚未处理。");
        state.put(203, "（非授权信息）服务器已成功处理了请求，但返回的信息可能来自另一来源。");
        state.put(204, "（无内容）服务器成功处理了请求，但没有返回任何内容。");
        state.put(205, "（重置内容）服务器成功处理了请求，但没有返回任何内容。");
        state.put(206, "（部分内容）服务器成功处理了部分 GET 请求。");
        //http状态返回代码 3xx （重定向）表示要完成请求，需要进一步操作。 通常，这些状态代码用来重定向。
        state.put(300, "（多种选择）针对请求，服务器可执行多种操作。服务器可根据请求者(user agent)选择一项操作，或提供操作列表供请求者选择。");
        state.put(301, "（永久移动）请求的网页已永久移动到新位置。服务器返回此响应（对 GET 或 HEAD 请求的响应）时，会自动将请求者转到新位置。");
        state.put(302, "（临时移动）服务器目前从不同位置的网页响应请求，但请求者应继续使用原有位置来进行以后的请求。");
        state.put(303, "（查看其他位置）请求者应当对不同的位置使用单独的 GET 请求来检索响应时，服务器返回此代码。");
        state.put(304, "（未修改）自从上次请求后，请求的网页未修改过。服务器返回此响应时，不会返回网页内容。");
        state.put(305, "（使用代理）请求者只能使用代理访问请求的网页。如果服务器返回此响应，还表示请求者应使用代理。");
        state.put(307, "（临时重定向）服务器目前从不同位置的网页响应请求，但请求者应继续使用原有位置来进行以后的请求。");
        //http状态返回代码 4xx（请求错误）这些状态代码表示请求可能出错，妨碍了服务器的处理。
        state.put(400, "（错误请求）服务器不理解请求的语法。");
        state.put(401, "（未授权）请求要求身份验证。对于需要登录的网页，服务器可能返回此响应。");
        state.put(403, "（禁止）服务器拒绝请求。");
        state.put(404, "（未找到）服务器找不到请求的网页。");
        state.put(405, "（方法禁用）禁用请求中指定的方法。");
        state.put(406, "（不接受）无法使用请求的内容特性响应请求的网页。");
        state.put(407, "（需要代理授权）此状态代码与 401（未授权）类似，但指定请求者应当授权使用代理。");
        state.put(408, "（请求超时）服务器等候请求时发生超时。");
        state.put(409, "（冲突）服务器在完成请求时发生冲突。 服务器必须在响应中包含有关冲突的信息。");
        state.put(410, "（已删除）如果请求的资源已永久删除，服务器就会返回此响应。");
        state.put(411, "（需要有效长度）服务器不接受不含有效内容长度标头字段的请求。");
        state.put(412, "（未满足前提条件）服务器未满足请求者在请求中设置的其中一个前提条件。");
        state.put(413, "（请求实体过大）服务器无法处理请求，因为请求实体过大，超出服务器的处理能力。");
        state.put(414, "（请求的 URI 过长）请求的 URI（通常为网址）过长，服务器无法处理。");
        state.put(415, "（不支持的媒体类型）请求的格式不受请求页面的支持。");
        state.put(416, "（请求范围不符合要求）如果页面无法提供请求的范围，则服务器会返回此状态代码。");
        state.put(417, "（未满足期望值）服务器未满足\"期望\"请求标头字段的要求。");
        state.put(499, "（客户端断开连接）服务端处理时间过长，导致客户端关闭了连接造成的。");
        //http状态返回代码 5xx（服务器错误）这些状态代码表示服务器在尝试处理请求时发生内部错误。 这些错误可能是服务器本身的错误，而不是请求出错。
        state.put(500, "（服务器内部错误）服务器遇到错误，无法完成请求。");
        state.put(501, "（尚未实施）服务器不具备完成请求的功能。例如，服务器无法识别请求方法时可能会返回此代码。");
        state.put(502, "（错误网关）服务器作为网关或代理，从上游服务器收到无效响应。");
        state.put(503, "（服务不可用）服务器目前无法使用（由于超载或停机维护）。 通常，这只是暂时状态。");
        state.put(504, "（网关超时）服务器作为网关或代理，但是没有及时从上游服务器收到请求。");
        state.put(505, "（HTTP 版本不受支持）服务器不支持请求中所用的 HTTP 协议版本。 ");
        //阿里云自定义代码 6xx (阿里服务器)您在使用站点监控时，返回的6XX状态码均为云监控自定义HTTP状态码
        state.put(610, "（HTTP连接超时）监测点探测您的网站时出现连接超时现象,云监控发出Http请求后5秒内没有回包，会返回610状态码。建议您设置报警规则时增加重试次数、采用组合报警等，以便优化以及提高报警准确率。");
        state.put(611, "（HTTP探测无法访问您的站点）请检测您的服务端是否限制了云监控的探测请求，如有开启防火墙，请添加最新的云监控IP地址。 ");
        state.put(613, "（DNS无法解析）查询域名解析失败，建议联系您的域名解析服务商协助检查域名解析是否不稳定。 ");
        state.put(615, "（返回的响应内容与用户设置的期望不符合）您在新建HTTP类型的站点监测时，如果使用了GET或POST请求方式，并在高级设置中填写了“匹配响应内容”，那么当匹配方式不符合您的设置时，就会返回615状态码。 ");
        state.put(631, "（TCP 连接失败）");
        state.put(650, "（DNS探测连接超时）");
        state.put(681, "（FTP探测无法连接您的站点）");
    }


    public static TreeMap<HttpState, String> stateEnum = new TreeMap<>();

    static {
        //http状态返回代码 1xx（临时响应）表示临时响应并需要请求者继续执行操作的状态代码。
        stateEnum.put(HttpState.HTTP_100, "（继续）请求者应当继续提出请求。 服务器返回此代码表示已收到请求的第一部分，正在等待其余部分。");
        stateEnum.put(HttpState.HTTP_101, "（切换协议）请求者已要求服务器切换协议，服务器已确认并准备切换。");
        //http状态返回代码 2xx （成功）表示成功处理了请求的状态代码。
        stateEnum.put(HttpState.HTTP_200, "（成功）服务器已成功处理了请求。 通常，这表示服务器提供了请求的网页。");
        stateEnum.put(HttpState.HTTP_201, "（已创建）请求成功并且服务器创建了新的资源。");
        stateEnum.put(HttpState.HTTP_202, "（已接受）服务器已接受请求，但尚未处理。");
        stateEnum.put(HttpState.HTTP_203, "（非授权信息）服务器已成功处理了请求，但返回的信息可能来自另一来源。");
        stateEnum.put(HttpState.HTTP_204, "（无内容）服务器成功处理了请求，但没有返回任何内容。");
        stateEnum.put(HttpState.HTTP_205, "（重置内容）服务器成功处理了请求，但没有返回任何内容。");
        stateEnum.put(HttpState.HTTP_206, "（部分内容）服务器成功处理了部分 GET 请求。");
        //http状态返回代码 3xx （重定向）表示要完成请求，需要进一步操作。 通常，这些状态代码用来重定向。
        stateEnum.put(HttpState.HTTP_300, "（多种选择）针对请求，服务器可执行多种操作。服务器可根据请求者(user agent)选择一项操作，或提供操作列表供请求者选择。");
        stateEnum.put(HttpState.HTTP_301, "（永久移动）请求的网页已永久移动到新位置。服务器返回此响应（对 GET 或 HEAD 请求的响应）时，会自动将请求者转到新位置。");
        stateEnum.put(HttpState.HTTP_302, "（临时移动）服务器目前从不同位置的网页响应请求，但请求者应继续使用原有位置来进行以后的请求。");
        stateEnum.put(HttpState.HTTP_303, "（查看其他位置）请求者应当对不同的位置使用单独的 GET 请求来检索响应时，服务器返回此代码。");
        stateEnum.put(HttpState.HTTP_304, "（未修改）自从上次请求后，请求的网页未修改过。服务器返回此响应时，不会返回网页内容。");
        stateEnum.put(HttpState.HTTP_305, "（使用代理）请求者只能使用代理访问请求的网页。如果服务器返回此响应，还表示请求者应使用代理。");
        stateEnum.put(HttpState.HTTP_307, "（临时重定向）服务器目前从不同位置的网页响应请求，但请求者应继续使用原有位置来进行以后的请求。");
        //http状态返回代码 4xx（请求错误）这些状态代码表示请求可能出错，妨碍了服务器的处理。
        stateEnum.put(HttpState.HTTP_400, "（错误请求）服务器不理解请求的语法。");
        stateEnum.put(HttpState.HTTP_401, "（未授权）请求要求身份验证。对于需要登录的网页，服务器可能返回此响应。");
        stateEnum.put(HttpState.HTTP_403, "（禁止）服务器拒绝请求。");
        stateEnum.put(HttpState.HTTP_404, "（未找到）服务器找不到请求的网页。");
        stateEnum.put(HttpState.HTTP_405, "（方法禁用）禁用请求中指定的方法。");
        stateEnum.put(HttpState.HTTP_406, "（不接受）无法使用请求的内容特性响应请求的网页。");
        stateEnum.put(HttpState.HTTP_407, "（需要代理授权）此状态代码与 401（未授权）类似，但指定请求者应当授权使用代理。");
        stateEnum.put(HttpState.HTTP_408, "（请求超时）服务器等候请求时发生超时。");
        stateEnum.put(HttpState.HTTP_409, "（冲突）服务器在完成请求时发生冲突。 服务器必须在响应中包含有关冲突的信息。");
        stateEnum.put(HttpState.HTTP_410, "（已删除）如果请求的资源已永久删除，服务器就会返回此响应。");
        stateEnum.put(HttpState.HTTP_411, "（需要有效长度）服务器不接受不含有效内容长度标头字段的请求。");
        stateEnum.put(HttpState.HTTP_412, "（未满足前提条件）服务器未满足请求者在请求中设置的其中一个前提条件。");
        stateEnum.put(HttpState.HTTP_413, "（请求实体过大）服务器无法处理请求，因为请求实体过大，超出服务器的处理能力。");
        stateEnum.put(HttpState.HTTP_414, "（请求的 URI 过长）请求的 URI（通常为网址）过长，服务器无法处理。");
        stateEnum.put(HttpState.HTTP_415, "（不支持的媒体类型）请求的格式不受请求页面的支持。");
        stateEnum.put(HttpState.HTTP_416, "（请求范围不符合要求）如果页面无法提供请求的范围，则服务器会返回此状态代码。");
        stateEnum.put(HttpState.HTTP_417, "（未满足期望值）服务器未满足\"期望\"请求标头字段的要求。");
        stateEnum.put(HttpState.HTTP_499, "（客户端断开连接）服务端处理时间过长，导致客户端关闭了连接造成的。");
        //http状态返回代码 5xx（服务器错误）这些状态代码表示服务器在尝试处理请求时发生内部错误。 这些错误可能是服务器本身的错误，而不是请求出错。
        stateEnum.put(HttpState.HTTP_500, "（服务器内部错误）服务器遇到错误，无法完成请求。");
        stateEnum.put(HttpState.HTTP_501, "（尚未实施）服务器不具备完成请求的功能。例如，服务器无法识别请求方法时可能会返回此代码。");
        stateEnum.put(HttpState.HTTP_502, "（错误网关）服务器作为网关或代理，从上游服务器收到无效响应。");
        stateEnum.put(HttpState.HTTP_503, "（服务不可用）服务器目前无法使用（由于超载或停机维护）。 通常，这只是暂时状态。");
        stateEnum.put(HttpState.HTTP_504, "（网关超时）服务器作为网关或代理，但是没有及时从上游服务器收到请求。");
        stateEnum.put(HttpState.HTTP_505, "（HTTP 版本不受支持）服务器不支持请求中所用的 HTTP 协议版本。 ");
        //阿里云自定义代码 6xx (阿里服务器)您在使用站点监控时，返回的6XX状态码均为云监控自定义HTTP状态码
        stateEnum.put(HttpState.HTTP_610, "（HTTP连接超时）监测点探测您的网站时出现连接超时现象,云监控发出Http请求后5秒内没有回包，会返回610状态码。建议您设置报警规则时增加重试次数、采用组合报警等，以便优化以及提高报警准确率。");
        stateEnum.put(HttpState.HTTP_611, "（HTTP探测无法访问您的站点）请检测您的服务端是否限制了云监控的探测请求，如有开启防火墙，请添加最新的云监控IP地址。 ");
        stateEnum.put(HttpState.HTTP_613, "（DNS无法解析）查询域名解析失败，建议联系您的域名解析服务商协助检查域名解析是否不稳定。 ");
        stateEnum.put(HttpState.HTTP_615, "（返回的响应内容与用户设置的期望不符合）您在新建HTTP类型的站点监测时，如果使用了GET或POST请求方式，并在高级设置中填写了“匹配响应内容”，那么当匹配方式不符合您的设置时，就会返回615状态码。 ");
        stateEnum.put(HttpState.HTTP_631, "（TCP 连接失败）");
        stateEnum.put(HttpState.HTTP_650, "（DNS探测连接超时）");
        stateEnum.put(HttpState.HTTP_681, "（FTP探测无法连接您的站点）");
    }
}
